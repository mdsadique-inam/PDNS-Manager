package mdsadiqueinam.github.io.pdns.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import mdsadiqueinam.github.io.pdns.PDNSServerException
import models.pdns.Error
import models.pdns.Server
import org.koin.core.annotation.Single
import recources.pdns.Servers

@Single
class ServerService(private val client: HttpClient) {
    suspend fun fetchServers(): Result<List<Server>> = kotlin.runCatching {
        val response = client.get(Servers())
        return when (response.status.value) {
            in 200..299 -> {
                val body = response.body<List<Server>>()
                Result.success(body)
            }
            else -> {
                val body = response.body<Error>()
                Result.failure(PDNSServerException(body))
            }
        }
    }
}