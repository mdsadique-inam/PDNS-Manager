package mdsadiqueinam.github.io.crypto.keygen

internal class SharedKeyGenerator(private val sharedKey: ByteArray) : BytesKeyGenerator {
    override val keyLength: Int
        get() = sharedKey.size

    override fun generateKey(): ByteArray {
        return this.sharedKey
    }
}