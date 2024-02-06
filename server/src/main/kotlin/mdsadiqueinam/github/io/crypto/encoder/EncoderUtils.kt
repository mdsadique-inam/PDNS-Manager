package mdsadiqueinam.github.io.crypto.encoder

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object EncoderUtils {
    /**
     * Constant time comparison to prevent against timing attacks.
     * @param expected
     * @param actual
     * @return
     */
    fun equals(expected: String?, actual: String?): Boolean {
        val expectedBytes = bytesUtf8(expected)
        val actualBytes = bytesUtf8(actual)
        return MessageDigest.isEqual(expectedBytes, actualBytes)
    }

    private fun bytesUtf8(s: String?): ByteArray? {
        // Need to check if Utf8.encode() runs in constant time (probably not).
        // This may leak the length of string.
        return if ((s != null)) s.toByteArray(StandardCharsets.UTF_8) else null
    }
}