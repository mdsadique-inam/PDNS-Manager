package services

import extensions.process
import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import models.Server
import resources.Servers


class ServerService(private val client: HttpClient) {
    suspend fun fetchServers(): Result<List<Server>> = runCatching {
        val response = client.get(Servers())
        return response.process<List<Server>>()
    }

    suspend fun fetchServer(id: String): Result<Server> = runCatching {
        val response = client.get(Servers.Id(id))
        return response.process<Server>()
    }
}