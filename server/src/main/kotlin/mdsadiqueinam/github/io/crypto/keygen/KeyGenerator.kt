package mdsadiqueinam.github.io.crypto.keygen

object KeyGenerator {
    /**
     * Create a [BytesKeyGenerator] that uses a [SecureRandom] to generate
     * keys of eight bytes in length.
     */
    fun secureRandom(): BytesKeyGenerator {
        return SecureRandomBytesKeyGenerator()
    }

    /**
     * Create a [BytesKeyGenerator] that uses a [SecureRandom] to generate
     * keys of a custom length.
     * @param keyLength the key length in bytes, e.g., 16, for a 16-byte key.
     */
    fun secureRandom(keyLength: Int): BytesKeyGenerator {
        return SecureRandomBytesKeyGenerator(keyLength)
    }
}