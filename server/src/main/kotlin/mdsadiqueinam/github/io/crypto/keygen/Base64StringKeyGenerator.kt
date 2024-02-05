package mdsadiqueinam.github.io.crypto.keygen

import java.util.*

class Base64StringKeyGenerator(
    private val encoder: Base64.Encoder = Base64.getEncoder(),
    keyLength: Int = DEFAULT_KEY_LENGTH
) : StringKeyGenerator {
    companion object {
        private const val DEFAULT_KEY_LENGTH = 32
    }

    private val keyGenerator: BytesKeyGenerator

    /**
     * Creates an instance with the provided key length and encoder.
     * @param encoder the encoder to use
     * @param keyLength the key length to use
     */
    init {
        require(keyLength >= DEFAULT_KEY_LENGTH) { "keyLength must be greater than or equal to $DEFAULT_KEY_LENGTH" }
        this.keyGenerator = KeyGenerator.secureRandom(keyLength)
    }

    override fun generateKey(): String {
        val key = keyGenerator.generateKey()
        val base64EncodedKey: ByteArray = encoder.encode(key)
        return String(base64EncodedKey)
    }
}