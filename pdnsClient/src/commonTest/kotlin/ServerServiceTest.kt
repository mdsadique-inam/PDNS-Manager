import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.coroutines.test.runTest
import exceptions.PDNSClientException
import io.ktor.http.*
import kotlinx.serialization.json.Json
import models.AutoPrimary
import services.ServerService
import kotlin.test.Test
import kotlin.test.assertTrue

class ServerServiceTest {

    private val client = PDNSClient.createHttpClient {
        defaultRequest {
            url("http://localhost:8081/api/v1/")
            header("X-API-Key", "secret")
            contentType(ContentType.Application.Json)
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

    @Test
    fun fetchAutoPrimaries() = runTest {
        val result = serverService.fetchAutoPrimaries("localhost")
        assertTrue(result.isSuccess)
    }

    @Test
    fun createAutoPrimary() = runTest {
        val body = AutoPrimary(
            "192.168.29.63",
            "ns1.sadique.dev",
            ""
        )
        val result = serverService.createAutoPrimary("localhost", body)
        assertTrue(result.isSuccess)
    }
}