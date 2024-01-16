package resources

import io.ktor.resources.*

/**
 * Get all CryptoKeys for a zone, except the privatekey
 *
 * Responses:
 * * 200 OK – List of Cryptokey objects Returns: array of [models.pdns.Cryptokey] objects
 * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
 * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
 * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
 * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
 *
 * @param parent The [Zones.Id] instance.
 */
@Resource("cryptokeys")
class Cryptokeys(val parent: Zones.Id) {

    /**
     * Creates a Cryptokey
     *
     * This method adds a new key to a zone. The key can either be generated or imported by supplying the content parameter.
     * if content, bits and algo are null,
     * a key will be generated based on the default-ksk-algorithm and default-ksk-size settings for a KSK and
     * the default-zsk-algorithm and default-zsk-size options for a ZSK.
     *
     * Responses:
     * * 201 Created – Created Returns: [models.pdns.Cryptokey] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
     *
     * @param parent The [Cryptokeys] instance.
     */
    @Resource("")
    class Post(val parent: Cryptokeys)

    /**
     * Returns all data about the CryptoKey, including the privatekey.
     *
     * Responses:
     * * 200 OK – Cryptokey Returns: [models.pdns.Cryptokey] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
     *
     * @param parent The [Cryptokeys] instance.
     * @param cryptokeyId The ID of the cryptokey to retrieve.
     */
    @Resource("{cryptokeyId}")
    class Id(val parent: Cryptokeys, val cryptokeyId: String) {

        /**
         * This method (de)activates a key from zone_name specified by cryptokey_id.
         *
         * Responses:
         * * 204 No Content – OK
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
         *
         * @param parent The [Cryptokeys.Id] instance.
         */
        @Resource("")
        class Put(val parent: Id)
    }

}