package mdsadiqueinam.github.io.crypto.util

object EncodingUtils {
    fun concatenate(vararg arrays: ByteArray): ByteArray {
        var length = 0
        for (array in arrays) {
            length += array.size
        }
        val newArray = ByteArray(length)
        var destPos = 0
        for (array in arrays) {
            System.arraycopy(array, 0, newArray, destPos, array.size)
            destPos += array.size
        }
        return newArray
    }

    /**
     * Extract a sub array of bytes out of the byte array.
     * @param array the byte array to extract from
     * @param beginIndex the beginning index of the sub array, inclusive
     * @param endIndex the ending index of the sub array, exclusive
     */
    fun subArray(array: ByteArray, beginIndex: Int, endIndex: Int): ByteArray {
        val length = endIndex - beginIndex
        val subArray = ByteArray(length)
        System.arraycopy(array, beginIndex, subArray, 0, length)
        return subArray
    }

}