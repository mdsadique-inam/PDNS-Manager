package services

import extensions.process
import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import models.AutoPrimary
import models.Server
import resources.AutoPrimaries
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
        return response.process()
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
        return response.process()
    }

    /**
     * Get a list of autoprimaries
     *
     * Responses:
     * * 200 OK – OK. Returns: [models.AutoPrimary] server object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The ID of the server.
     * @return A [Result] with the [List] of [AutoPrimary]s.
     */
    suspend fun fetchAutoPrimaries(serverId: String): Result<List<AutoPrimary>> = runCatching {
        val response = client.get(AutoPrimaries(serverId))
        return response.process()
    }

    /**
     * Add an autoprimary
     *
     * This method adds a new autoprimary server.
     *
     * Responses:
     * * 201 Created – Created
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The ID of the server to create the autoprimary on.
     * @param body The [AutoPrimary] to create.
     * @return A [Result] with [Unit].
     */
    suspend fun createAutoPrimary(serverId: String, body: AutoPrimary): Result<Unit> = runCatching {
        val response = client.post(AutoPrimaries(serverId)) {
            setBody(body)
        }
        return response.process()
    }

    /**
     * Delete the autoprimary entry
     *
     * Responses:
     * * 204 No Content – OK, key was deleted
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The ID of the server to delete the autoprimary on.
     * @param ip The IP of the autoprimary to delete.
     * @param nameserver The nameserver of the autoprimary to delete.
     * @return A [Result] with [Unit].
     */
    suspend fun deleteAutoPrimary(serverId: String, ip: String, nameserver: String): Result<Unit> = runCatching {
        val response = client.delete(AutoPrimaries.Delete(AutoPrimaries(serverId), ip, nameserver))
        return response.process()
    }
}