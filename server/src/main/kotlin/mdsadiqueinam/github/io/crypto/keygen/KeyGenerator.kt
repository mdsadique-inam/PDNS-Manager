package mdsadiqueinam.github.io.crypto.keygen

object KeyGenerator {
    /**
     * Create a [BytesKeyGenerator] that uses a [java.security.SecureRandom] to generate
     * keys of eight bytes in length.
     */
    fun secureRandom(): BytesKeyGenerator {
        return SecureRandomBytesKeyGenerator()
    }

    /**
     * Create a [BytesKeyGenerator] that uses a [java.security.SecureRandom] to generate
     * keys of a custom length.
     * @param keyLength the key length in bytes, e.g., 16, for a 16-byte key.
     */
    fun secureRandom(keyLength: Int): BytesKeyGenerator {
        return SecureRandomBytesKeyGenerator(keyLength)
    }

    /**
     * Create a [BytesKeyGenerator] that returns a single, shared
     * [java.security.SecureRandom] key of a custom length.
     * @param keyLength the key length in bytes, e.g. 16, for a 16 byte key.
     */
    fun shared(keyLength: Int): BytesKeyGenerator {
        return SharedKeyGenerator(secureRandom(keyLength).generateKey())
    }

    /**
     * Creates a [StringKeyGenerator] that hex-encodes [java.security.SecureRandom] keys of
     * 8 bytes in length.
     * The hex-encoded string is keyLength * 2 characters in length.
     */
    fun string(): StringKeyGenerator {
        return HexEncodingStringKeyGenerator(secureRandom())
    }
}