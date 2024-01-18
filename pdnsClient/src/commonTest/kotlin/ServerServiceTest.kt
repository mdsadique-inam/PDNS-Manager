import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.coroutines.test.runTest
import exceptions.PDNSClientException
import services.ServerService
import kotlin.test.Test

class ServerServiceTest {

    private val client = PDNSClient.createHttpClient {
        defaultRequest {
            host = "localhost"
            port = 8081
            header("X-API-Key", "changeme")
        }
    }
    private val serverService = ServerService(client)

    @Test
    fun fetchServer() = runTest {
        val result = serverService.fetchServers()
        result.exceptionOrNull()?.let {
            when(it) {
                is PDNSClientException -> println(it.error)
                else -> println(it)
            }
        }
        println(result)
    }
}