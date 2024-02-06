package mdsadiqueinam.github.io.crypto.keygen

import org.bouncycastle.util.encoders.Hex


internal class HexEncodingStringKeyGenerator(private val keyGenerator: BytesKeyGenerator) : StringKeyGenerator {
    override fun generateKey(): String {
        return String(Hex.encode(keyGenerator.generateKey()))
    }
}