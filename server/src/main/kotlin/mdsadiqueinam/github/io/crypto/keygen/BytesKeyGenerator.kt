package mdsadiqueinam.github.io.crypto.keygen

interface BytesKeyGenerator {
    /**
     * Get the length, in bytes, of keys created by this generator. Unique keys are
     * at least eight bytes in length.
     */
    val keyLength: Int

    /**
     * Generate a new key.
     */
    fun generateKey(): ByteArray
}