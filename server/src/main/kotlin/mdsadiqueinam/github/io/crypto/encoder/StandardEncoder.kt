package mdsadiqueinam.github.io.crypto.encoder

import mdsadiqueinam.github.io.crypto.keygen.BytesKeyGenerator
import mdsadiqueinam.github.io.crypto.keygen.KeyGenerator
import mdsadiqueinam.github.io.crypto.util.EncodingUtils
import org.bouncycastle.util.encoders.Hex
import java.nio.charset.StandardCharsets
import java.security.MessageDigest


/**
 * This [Encoder] is provided for legacy purposes only and is not considered
 * secure.
 *
 * A standard `PasswordEncoder` implementation that uses SHA-256 hashing with 1024
 * iterations and a random 8-byte random salt value. It uses an additional system-wide
 * secret value to provide additional protection.
 *
 *
 * The digest algorithm is invoked on the concatenated bytes of the salt, secret and
 * password.
 *
 *
 * If you are developing a new system,
 * [mdsadiqueinam.github.io.crypto.bcrypt.BCryptPasswordEncoder] is a better
 * choice both in terms of security and interoperability with other languages.
 *
 * @author Keith Donald
 * @author Luke Taylor
 */
class StandardEncoder private constructor(algorithm: String, secret: String) : Encoder {
    private val digester: Digester = Digester(
        algorithm,
        DEFAULT_ITERATIONS
    )

    private val secret: ByteArray = secret.toByteArray(StandardCharsets.UTF_8)

    private val saltGenerator: BytesKeyGenerator = KeyGenerator.secureRandom()

    /**
     * Constructs a standard password encoder with a secret value which is also included
     * in the password hash.
     * @param secret the secret key used in the encoding process (should not be shared)
     */
    constructor(secret: String = "") : this("SHA-256", secret)

    override fun encode(rawPassword: CharSequence): String {
        return encode(rawPassword, saltGenerator.generateKey())
    }

    private fun encode(rawPassword: CharSequence, salt: ByteArray): String {
        val digest = digest(rawPassword.toString(), salt)
        return String(Hex.encode(digest))
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        val digested = decode(encodedPassword)
        val salt = EncodingUtils.subArray(digested, 0, saltGenerator.keyLength)
        return MessageDigest.isEqual(digested, digest(rawPassword.toString(), salt))
    }

    private fun digest(rawPassword: String, salt: ByteArray): ByteArray {
        val digest =
            digester.digest(EncodingUtils.concatenate(salt, this.secret, rawPassword.toByteArray(StandardCharsets.UTF_8)))
        return EncodingUtils.concatenate(salt, digest)
    }

    private fun decode(encodedPassword: String): ByteArray {
        return Hex.decode(encodedPassword)
    }

    companion object {
        private const val DEFAULT_ITERATIONS = 1024u
    }
}