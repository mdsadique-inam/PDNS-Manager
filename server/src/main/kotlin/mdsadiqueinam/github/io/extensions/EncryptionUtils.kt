package mdsadiqueinam.github.io.extensions

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

fun String.hashWithSHA256(): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashBytes = messageDigest.digest(toByteArray(StandardCharsets.UTF_8))
    return hashBytes.toHex()
}

@Suppress("MagicNumber", "SpellCheckingInspection")
fun ByteArray.toHex(): String {
    val hexChars = "0123456789ABCDEF"
    val hex = StringBuilder(size * 2)
    for (byte in this) {
        val index = byte.toInt() and 0xFF
        hex.append(hexChars[index shr 4])
        hex.append(hexChars[index and 0x0F])
    }
    return hex.toString()
}
