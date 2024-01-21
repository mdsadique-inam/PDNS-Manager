package services

import extensions.process
import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import models.*
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

    /**
     * Search the data inside PowerDNS
     *
     * Search the data inside PowerDNS for search_term and return at most max_results.
     * This includes zones, records and comments.
     * The * character can be used in search_term as a wildcard character
     * and the ? character can be used as a wildcard for a single character.
     *
     * Responses:
     * * 200 OK – Returns a JSON array with results Returns: array of [models.SearchResult] objects
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The ID of the server to search.
     * @param query The query to search for.
     * @param max The maximum number of results to return.
     * @param objectType The type of object to search for.
     * @return A [Result] with the [List] of [SearchResult]s.
     */
    suspend fun search(serverId: String, query: String, max: Int, objectType: SearchType): Result<List<SearchResult>> = runCatching {
        val response = client.get(Servers.Search(serverId, query, max, objectType))
        return response.process()
    }


    /**
     * Query statistics.
     *
     * Query PowerDNS internal statistics.
     *
     * Responses:
     * * 200 OK – List of [models.Statistic] Returns: array
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – Returned when a non-existing statistic name has been requested.
     * Contains an error message
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The ID of the server to retrieve the statistics from.
     * @param statistic The name of the statistic to retrieve.
     * When set to the name of a specific statistic, only this value is returned.
     * If no statistic with that name exists, the response has a 422 status and an error message.
     * @param includerings “true” (default) or “false”
     * whether to include the Ring items, which can contain thousands of log messages or queried domains.
     * Setting this to “false” may make the response a lot smaller.
     */
    suspend fun statistics(serverId: String, statistic: StatisticType? = null, includerings: Boolean? = null): Result<List<Statistic>> = runCatching {
        val response = client.get(Servers.Statistics(Servers.Id(serverId), statistic, includerings))
        return response.process()
    }

    suspend fun cacheFlush(serverId: String, domain: String): Result<CacheFlushResult> = runCatching {
        val response = client.put(Servers.cacheFlush(serverId, domain))
        return response.process()
    }
}