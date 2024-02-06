package mdsadiqueinam.github.io.crypto.encoder

import mdsadiqueinam.github.io.crypto.keygen.BytesKeyGenerator
import mdsadiqueinam.github.io.crypto.keygen.KeyGenerator
import mdsadiqueinam.github.io.crypto.util.EncodingUtils
import org.bouncycastle.util.encoders.Hex
import java.security.MessageDigest


abstract class AbstractEncoder protected constructor() : Encoder {
    private val saltGenerator = KeyGenerator.secureRandom()

    override fun encode(rawPassword: CharSequence): String {
        val salt = saltGenerator.generateKey()
        val encoded = encodeAndConcatenate(rawPassword, salt)
        return String(Hex.encode(encoded))
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        val digested = Hex.decode(encodedPassword)
        val salt: ByteArray = EncodingUtils.subArray(digested, 0, saltGenerator.keyLength)
        return matches(digested, encodeAndConcatenate(rawPassword, salt))
    }

    protected abstract fun encode(rawPassword: CharSequence, salt: ByteArray): ByteArray

    protected fun encodeAndConcatenate(rawPassword: CharSequence, salt: ByteArray): ByteArray {
        return EncodingUtils.concatenate(salt, encode(rawPassword, salt))
    }

    /**
     * Constant time comparison to prevent against timing attacks.
     */
    protected fun matches(expected: ByteArray, actual: ByteArray): Boolean {
        return MessageDigest.isEqual(expected, actual)
    }
}