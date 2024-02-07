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
 * A standard `Encoder` implementation that uses SHA-256 hashing with 1024
 * iterations and a random 8-byte random salt value. It uses an additional system-wide
 * secret value to provide additional protection.
 *
 *
 * The digest algorithm is invoked on the concatenated bytes of the salt, secret and hash.
 *
 *
 * If you are developing a new system,
 * [mdsadiqueinam.github.io.crypto.bcrypt.BCryptEncoder] is a better
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
     * Constructs a standard encoder with a secret value which is also included
     * in the hash.
     * @param secret the secret key used in the encoding process (should not be shared)
     */
    constructor(secret: String = "") : this("SHA-256", secret)

    override fun encode(raw: CharSequence): String {
        return encode(raw, saltGenerator.generateKey())
    }

    private fun encode(raw: CharSequence, salt: ByteArray): String {
        val digest = digest(raw.toString(), salt)
        return String(Hex.encode(digest))
    }

    override fun matches(raw: CharSequence, encoded: String): Boolean {
        val digested = decode(encoded)
        val salt = EncodingUtils.subArray(digested, 0, saltGenerator.keyLength)
        return MessageDigest.isEqual(digested, digest(raw.toString(), salt))
    }

    private fun digest(raw: String, salt: ByteArray): ByteArray {
        val digest =
            digester.digest(EncodingUtils.concatenate(salt, this.secret, raw.toByteArray(StandardCharsets.UTF_8)))
        return EncodingUtils.concatenate(salt, digest)
    }

    private fun decode(encoded: String): ByteArray {
        return Hex.decode(encoded)
    }

    companion object {
        private const val DEFAULT_ITERATIONS = 1024u
    }
}