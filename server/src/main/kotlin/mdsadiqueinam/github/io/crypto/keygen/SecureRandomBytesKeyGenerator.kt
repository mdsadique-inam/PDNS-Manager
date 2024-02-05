package mdsadiqueinam.github.io.crypto.keygen

import java.security.SecureRandom

class SecureRandomBytesKeyGenerator(override val keyLength: Int = DEFAULT_KEY_LENGTH) : BytesKeyGenerator {
    companion object {
        private const val DEFAULT_KEY_LENGTH = 8
    }

    private val random: SecureRandom = SecureRandom()

    /**
     * Creates a secure random key generator with a custom key length.
     */
    override fun generateKey(): ByteArray {
        val bytes = ByteArray(this.keyLength)
        random.nextBytes(bytes)
        return bytes
    }
}