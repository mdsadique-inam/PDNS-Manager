import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.coroutines.test.runTest
import exceptions.PDNSClientException
import kotlinx.serialization.json.Json
import services.ServerService
import kotlin.test.Test
import kotlin.test.assertTrue

class ServerServiceTest {

    private val client = PDNSClient.createHttpClient {
        defaultRequest {
            url("http://localhost:8081/api/v1/")
            header("X-API-Key", "secret")
        }
    }
    private val serverService = ServerService(client)

    @Test
    fun fetchServer() = runTest {
        val result = serverService.fetchServers()
        assertTrue(result.isSuccess)
    }

    @Test
    fun fetchServerWithId() = runTest {
        val result = serverService.fetchServer("localhost")
        assertTrue(result.isSuccess)
    }
}