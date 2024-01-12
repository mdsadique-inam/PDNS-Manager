package recources.pdns

import io.ktor.resources.*

/**
 * Get all the Metadata associated with the zone.
 *
 * Responses:
 * * 200 OK – List of Metadata objects Returns: array of [models.pdns.Metadata] objects
 * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
 * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
 * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
 * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
 *
 * @param parent The [Zones.Id] instance.
 */
@Resource("metadata")
class Metadata(val parent: Zones.Id) {

    /**
     * Creates a set of metadata entries
     *
     * Creates a set of metadata entries of given kind for the zone. Existing metadata entries for the zone with the same kind are not overwritten.
     *
     * Responses:
     * * 204 No Content – OK
     * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
     *
     * @param parent The [Metadata] instance.
     */
    @Resource("")
    class Post(val parent: Metadata)

    /**
     * Get the content of a single kind of domain metadata as a Metadata object.
     *
     * Responses:
     * * 200 OK – Metadata object with list of values Returns: [models.pdns.Metadata] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
     *
     * @param parent The [Metadata] instance.
     * @param kind The kind of metadata to retrieve.
     */
    @Resource("{kind}")
    class Id(val parent: Metadata, val kind: String) {

        /**
         * Replace the content of a single kind of domain metadata.
         *
         * Creates a set of metadata entries of given kind for the zone. Existing metadata entries for the zone with the same kind are removed.
         *
         * Responses:
         * * 200 OK – Metadata object with list of values Returns: [models.pdns.Metadata] object
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
         *
         * @param parent The [Metadata.Id] instance.
         */
        @Resource("")
        class Put(val parent: Id)

        /**
         * Delete all items of a single kind of domain metadata.
         *
         * Responses:
         * * 204 No Content – OK
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
         *
         * @param parent The [Metadata.Id] instance.
         */
        @Resource("")
        class Delete(val parent: Id)
    }
}