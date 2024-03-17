package mdsadiqueinam.github.io.repositories

import PDNSClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import services.ZoneService

@Module
@ComponentScan
class RepositoryModule {
    private val client = PDNSClient.createHttpClient {
        defaultRequest {
            url("http://localhost:8081/api/v1/")
            header("X-API-Key", "secret")
            contentType(ContentType.Application.Json)
        }
    }

    @Single
    fun zoneService() = ZoneService(client)
}