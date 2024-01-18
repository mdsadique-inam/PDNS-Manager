package resources

import io.ktor.resources.*


/**
 * List all servers
 *
 * Responses
 * * 200 OK – An array of servers Returns: array of [models.pdns.Server] objects
 * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
 * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
 * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
 * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
 */
@Resource("/servers")
class Servers {

    /**
     * Get server details
     *
     * Responses
     * * 200 OK – An server Returns: [models.pdns.Server] object
     * * 400 Bad Request – The supplied request was not valid Returns: Er[models.pdns.Error]ror object
     * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
     *
     * @param parent The parent servers.
     * @param id The ID of the server to retrieve.
     */
    @Resource("{serverId}")
    class Id(val parent: Servers = Servers(), val serverId: String) {
        constructor(serverId: String) : this(Servers(), serverId)
    }
}