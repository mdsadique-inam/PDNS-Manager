package recources.pdns

import io.ktor.resources.*

/**
 * Get a list of autoprimaries
 *
 * Responses:
 * * 200 OK – OK. Returns: [models.pdns.AutoPrimary] server object
 * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
 * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
 * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
 * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
 *
 * @param parent The [Servers.Id] instance.
 */
@Resource("autoprimaries")
class AutoPrimaries(val parent: Servers.Id) {

    /**
     * Add an autoprimary
     *
     * This method adds a new autoprimary server.
     *
     * Responses:
     * * 201 Created – Created
     * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
     *
     * @param parent The [AutoPrimaries] instance.
     */
    @Resource("")
    class Post(val parent: AutoPrimaries)

    /**
     * Delete the autoprimary entry
     *
     * Responses:
     * * 204 No Content – OK, key was deleted
     * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
     *
     * @param parent The [AutoPrimaries] instance.
     * @param ip The IP of the autoprimary to delete.
     * @param nameserver The nameserver of the autoprimary to delete.
     */
    @Resource("{ip}/{nameserver}")
    class Delete(val parent: AutoPrimaries, val ip: String, val nameserver: String)
}