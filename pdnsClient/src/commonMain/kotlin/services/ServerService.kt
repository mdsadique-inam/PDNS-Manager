package services

import extensions.process
import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import models.Server
import resources.Servers


class ServerService(private val client: HttpClient) {

    /**
     * List all servers
     *
     * Responses
     * * 200 OK – An array of servers Returns: array of [models.Server] objects
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @return [Result] with [List] of [Server]s
     */
    suspend fun fetchServers(): Result<List<Server>> = runCatching {
        val response = client.get(Servers())
        return response.process<List<Server>>()
    }

    /**
     * Get server details
     *
     * Responses
     * * 200 OK – An server Returns: [models.Server] object
     * * 400 Bad Request – The supplied request was not valid Returns: Er[models.Error]ror object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param id [String] server id
     * @return [Result] with [Server]
     */
    suspend fun fetchServer(id: String): Result<Server> = runCatching {
        val response = client.get(Servers.Id(id))
        return response.process<Server>()
    }
}