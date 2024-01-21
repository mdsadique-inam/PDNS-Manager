import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.coroutines.test.runTest
import services.CryptokeyService
import kotlin.test.Test
import kotlin.test.assertTrue

class CryptokeyServiceTest {

    private val client = PDNSClient.createHttpClient {
        defaultRequest {
            url("http://localhost:8081/api/v1/")
            header("X-API-Key", "secret")
        }
    }
    private val cryptokeyService = CryptokeyService(client)

    @Test
    fun fetchCryptokeys() = runTest {
        val result = cryptokeyService.fetchCryptokeys("localhost", "sadique.dev.")
        assertTrue(result.isSuccess)
        println(result.getOrThrow())
    }

    @Test
    fun fetchCryptokey() = runTest {
        val result = cryptokeyService.fetchCryptokey("localhost", "sadique.dev.", 3)
        assertTrue(result.isSuccess)
        println(result.getOrThrow())
    }
}