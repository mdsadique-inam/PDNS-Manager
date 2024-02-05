package mdsadiqueinam.github.io.extensions

import mdsadiqueinam.github.io.utils.Encryption


fun String.hash(algorithm: Encryption.Algorithm = Encryption.Algorithm.ARGON2): String {
    return Encryption.instance.hash(this, algorithm)
}

fun String.verifyHash(input: String, algorithm: Encryption.Algorithm = Encryption.Algorithm.ARGON2): Boolean {
    return Encryption.instance.verify(input, this, algorithm)
}

@Suppress("SpellCheckingInspection")
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
