package recources.pdns

import io.ktor.resources.*

/**
 * Get all TSIGKeys on the server, except the actual key
 *
 * Responses:
 * * 200 OK – List of TSIGKey objects Returns: array of [models.pdns.TSIGKey] objects
 * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
 * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
 * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
 * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
 *
 * @param parent The [Servers.Id] instance.
 */
@Resource("tsigkeys")
class TSIGKeys(val parent: Servers.Id) {

    /**
     * Add a TSIG key
     *
     * This methods add a new TSIGKey. The actual key can be generated by the server or be provided by the client.
     *
     * Responses:
     * * 201 Created – Created Returns: [models.pdns.TSIGKey] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
     * * 409 Conflict – An item with this name already exists Returns: [models.pdns.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
     *
     * @param parent The [TSIGKeys] instance.
     */
    @Resource("")
    class Post(val parent: TSIGKeys)

    /**
     * Get a specific TSIGKeys on the server, including the actual key
     *
     * Responses:
     * * 200 OK – OK. Returns: [models.pdns.TSIGKey] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
     *
     * @param parent The [TSIGKeys] instance.
     * @param id The ID of the TSIGKey to retrieve.
     */
    @Resource("{id}")
    class Id(val parent: TSIGKeys, val id: String) {

        /**
         * The TSIGKey at id can be changed in multiple ways:
         * * Changing the Name, this will remove the key with id after adding.
         * * Changing the Algorithm
         * * Changing the Key
         *
         * Only the relevant fields have to be provided in the request body.
         *
         * Responses:
         * * 200 OK – OK. TSIGKey is changed. Returns: [models.pdns.TSIGKey] object
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 409 Conflict – An item with this name already exists Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
         *
         * @param parent The [TSIGKeys.Id] instance.
         */
        @Resource("")
        class Put(val parent: Id)

        /**
         * Delete the TSIGKey with id
         *
         * Responses:
         * * 204 No Content – OK, key was deleted
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
         *
         */
        @Resource("")
        class Delete(val parent: Id)
    }
}