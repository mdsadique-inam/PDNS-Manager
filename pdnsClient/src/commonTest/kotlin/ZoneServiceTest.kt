import exceptions.PDNSClientException
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import models.Zone
import services.ZoneService
import kotlin.test.Test
import kotlin.test.assertTrue

class ZoneServiceTest {
    private val client = PDNSClient.createHttpClient {
        defaultRequest {
            url("http://localhost:8081/api/v1/")
            header("X-API-Key", "secret")
            contentType(ContentType.Application.Json)
        }
    }
    private val zoneService = ZoneService(client)

    @Test
    fun fetchZones() = runTest {
        val result = zoneService.fetchZones("localhost")
        assertTrue(result.isSuccess)
        println(result.getOrThrow())
    }

    @Test
    fun createNewZone() = runTest {
        val zone = Zone(
            name = "sadique.dev.",
            nameservers = listOf("ns1.sadique.dev.", "ns2.sadique.dev."),
        )
        val result = zoneService.createZone("localhost", zone)
        assertTrue(result.isSuccess)
    }

    @Test
    fun fetchZone() = runTest {
        val result = zoneService.fetchZone("localhost", "sadique.dev.")
        assertTrue(result.isSuccess)
    }

    @Test
    fun updateZone() = runTest {
        val zone = Zone(
            name = "sadique.dev.",
            nameservers = listOf("ns1.sadique.dev.", "ns2.sadique.dev."),
            id = "sadique.dev.",
        )
        val result = zoneService.updateZone("localhost", zone)
        assertTrue(result.isSuccess)
    }
}