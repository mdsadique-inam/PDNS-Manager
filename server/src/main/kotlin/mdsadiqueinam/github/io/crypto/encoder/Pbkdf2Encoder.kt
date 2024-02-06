package mdsadiqueinam.github.io.crypto.encoder

import mdsadiqueinam.github.io.crypto.keygen.BytesKeyGenerator
import mdsadiqueinam.github.io.crypto.keygen.KeyGenerator
import mdsadiqueinam.github.io.crypto.util.EncodingUtils
import org.bouncycastle.util.encoders.Hex
import java.nio.charset.StandardCharsets
import java.security.GeneralSecurityException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

@Suppress("SpellCheckingInspection")
/**
 * A [Encoder] implementation that uses PBKDF2 with:
 * - a configurable random salt value length (default is [DEFAULT_SALT_LENGTH] bytes)
 * - a configurable number of iterations (default is [DEFAULT_ITERATIONS])
 * - a configurable key derivation function (see [SecretKeyFactoryAlgorithm])
 * - a configurable secret appended to the random salt (default is empty)
 *
 * The algorithm is invoked on the concatenated bytes of the salt, secret and password.
 *
 * Constructs a PBKDF2 password encoder with a secret value as well as salt length,
 * iterations and algorithm.
 * @param secret the secret
 * @param saltLength the salt length (in bytes)
 * @param iterations the number of iterations. Users should aim for taking about .5
 * seconds on their own system.
 * @param secretKeyFactoryAlgorithm the algorithm to use
 */
class Pbkdf2Encoder(
    secret: String = "",
    saltLength: Int = DEFAULT_SALT_LENGTH,
    private val iterations: Int = DEFAULT_ITERATIONS,
    secretKeyFactoryAlgorithm: SecretKeyFactoryAlgorithm = DEFAULT_ALGORITHM
) : Encoder {
    companion object {
        private const val DEFAULT_SALT_LENGTH = 16
        private const val DEFAULT_ITERATIONS = 310000
        private val DEFAULT_ALGORITHM = SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
        private const val DEFAULT_HASH_WIDTH = 256
    }

    private val saltGenerator: BytesKeyGenerator = KeyGenerator.secureRandom(saltLength)

    private val secret: ByteArray = secret.toByteArray(StandardCharsets.UTF_8)

    private lateinit var algorithm: String

    private var hashWidth = DEFAULT_HASH_WIDTH

    // @formatter:off
    /*
       The length of the hash should be derived from the hashing algorithm.

       For example:
           SHA-1 - 160 bits (20 bytes)
           SHA-256 - 256 bits (32 bytes)
           SHA-512 - 512 bits (64 bytes)

       However, the original configuration for PBKDF2 was hashWidth=256 and algorithm=SHA-1, which is incorrect.
       The default configuration has been updated to hashWidth=256 and algorithm=SHA-256 (see gh-10506).
       In order to preserve backwards compatibility, the variable 'overrideHashWidth' has been introduced
       to indicate usage of the deprecated constructor that honors the hashWidth parameter.
        */
    // @formatter:on
    private var overrideHashWidth = true

    /**
     * Sets if the resulting hash should be encoded as Base64. The default is false, which
     * means it will be encoded in Hex.
     */
    var encodeHashAsBase64 = false

    init {
        setAlgorithm(secretKeyFactoryAlgorithm)
    }

    /**
     * Sets the algorithm to use. See [SecretKeyFactory
 * Algorithms](https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory)
     * @param secretKeyFactoryAlgorithm the algorithm to use (i.e.
     * `SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA1`,
     * `SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256`,
     * `SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512`)
     * @since 5.0
     */
    fun setAlgorithm(secretKeyFactoryAlgorithm: SecretKeyFactoryAlgorithm) {
        val algorithmName = secretKeyFactoryAlgorithm.name
        try {
            SecretKeyFactory.getInstance(algorithmName)
            this.algorithm = algorithmName
        } catch (ex: NoSuchAlgorithmException) {
            throw IllegalArgumentException("Invalid algorithm '$algorithmName'.", ex)
        }
        if (this.overrideHashWidth) {
            this.hashWidth = if (SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA1 == secretKeyFactoryAlgorithm) 160
            else if (SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256 == secretKeyFactoryAlgorithm) 256 else 512
        }
    }

    override fun encode(rawPassword: CharSequence): String {
        val salt = saltGenerator.generateKey()
        val encoded = encode(rawPassword, salt)
        return encode(encoded)
    }

    private fun encode(rawPassword: CharSequence, salt: ByteArray): ByteArray {
        try {
            val spec = PBEKeySpec(
                rawPassword.toString().toCharArray(),
                EncodingUtils.concatenate(salt, this.secret), this.iterations, this.hashWidth
            )
            val skf = SecretKeyFactory.getInstance(this.algorithm)
            return EncodingUtils.concatenate(salt, skf.generateSecret(spec).encoded)
        } catch (ex: GeneralSecurityException) {
            throw IllegalStateException("Could not create hash", ex)
        }
    }

    private fun encode(bytes: ByteArray): String {
        if (this.encodeHashAsBase64) {
            return Base64.getEncoder().encodeToString(bytes)
        }
        return String(Hex.encode(bytes))
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        val digested = decode(encodedPassword)
        val salt = EncodingUtils.subArray(
            digested, 0,
            saltGenerator.keyLength
        )
        return MessageDigest.isEqual(digested, encode(rawPassword, salt))
    }

    private fun decode(encodedBytes: String): ByteArray {
        if (this.encodeHashAsBase64) {
            return Base64.getDecoder().decode(encodedBytes)
        }
        return Hex.decode(encodedBytes)
    }
    /**
     * The Algorithm used for creating the [SecretKeyFactory]
     */
    enum class SecretKeyFactoryAlgorithm {
        PBKDF2WithHmacSHA1, PBKDF2WithHmacSHA256, PBKDF2WithHmacSHA512
    }
}