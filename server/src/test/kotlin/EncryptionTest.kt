import mdsadiqueinam.github.io.utils.Encryption
import kotlin.test.Test

class EncryptionTest {
    @Test
    fun `test encryption and decryption`() {
        val key = "1234567890123456"
        val text = "Hello, World!"
        val encryption = Encryption.encryption(key)
        val hash = encryption.hash(key)
        println(hash)
    }
}