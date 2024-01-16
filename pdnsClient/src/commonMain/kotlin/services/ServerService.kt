package services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import models.Server
import models.Error
import resources.Servers

class ServerService(private val client: HttpClient) {
    suspend fun fetchServers(): Result<List<Server>> = runCatching {
        val response = client.get(Servers())
        return when (response.status.value) {
            in 200..299 -> {
                val body = response.body<List<Server>>()
                Result.success(body)
            }
            in intArrayOf(400,404,422,500) -> {
                val body = response.body<Error>()
                Result.failure(PDNSClientException(body))
            }
            else -> {
                Result.failure(Throwable("Unknown error"))
            }
        }
    }
}


class PDNSClientException(error: Error) : Throwable(error.error)