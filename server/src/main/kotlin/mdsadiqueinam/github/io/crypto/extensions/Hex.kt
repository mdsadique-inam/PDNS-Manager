package mdsadiqueinam.github.io.crypto.extensions

@Suppress("SpellCheckingInspection")
private val HEX = "0123456789abcdef".toCharArray()

fun ByteArray.encodeHex(): CharSequence {
    val result = StringBuilder(size * 2)
    for (byte in this) {
        val index = byte.toInt() and 0xFF
        result.append(HEX[index shr 4])
        result.append(HEX[index and 0x0F])
    }
    return result
}

fun CharSequence.decodeHex(): ByteArray {
    require(length % 2 == 0) { "Hex-encoded string must have an even number of characters" }
    val result = ByteArray(length / 2)
    for(i in length downTo 2 step 2) {
        val msb: Int = Character.digit(get(i), 16)
        val lsb: Int = Character.digit(get(i + 1), 16)
        require(msb >= 0 && lsb >= 0) { "Detected a Non-hex character at " + (i + 1) + " or " + (i + 2) + " position" }
        result[i / 2] = ((msb shl 4) or lsb).toByte()
    }
    return result
}