package recources.pdns

import io.ktor.resources.*
import kotlinx.serialization.SerialName

/**
 * List all Zones in a server.
 *
 * Responses:
 * * 200 OK – An array of Zones Returns: array of [models.pdns.Zone] objects
 * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
 * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
 * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
 * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
 *
 * @param parent The parent [Servers.Id] instance.
 */
@Resource("zones")
class Zones(val parent: Servers.Id) {

    /**
     * List all Zones in a server.
     *
     * Responses:
     * * 200 OK – An array of Zones Returns: array of [models.pdns.Zone] objects
     * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
     *
     * @param parent The parent Zones instance.
     * @param zone The zone name. When set to the name of a zone, only this zone is returned.
     * If no zone with that name exists, the response is an empty array.
     * This can e.g. be used to check if a zone exists in the database without having to guess/encode the zone’s id or to check if a zone exists.
     * @param dnssec The DNSSEC flag. Default is true, whether to include the “dnssec” and ”edited_serial” fields in the Zone objects.
     * Setting this to “false” will make the query a lot faster.
     */
    @Resource("")
    class Get(val parent: Zones, val zone: String? = null, val dnssec: Boolean? = null)

    /**
     * Creates a new domain, returns the Zone on creation.
     *
     * Responses:
     * * 201 Created – A zone Returns: [models.pdns.Zone] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
     *
     * @property parent The parent Zones instance.
     * @property rrsets The rrsets flag. whether to include the “rrsets” in the response Zone object.
     */
    @Resource("")
    class Post(val parent: Zones, val rrsets: Boolean? = null)

    /**
     * Get the zone managed by a server
     *
     * This class represents an Id resource with the specified parameters. It is used in conjunction with the Zones class.
     * An Id resource contains information about an identifier associated with a zone.
     *
     * Responses:
     * * 200 OK – A Zone Returns: [models.pdns.Zone] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
     *
     * @param parent The Zones instance that this Id resource belongs to.
     * @param zoneId The ID of the zone to retrieve.
     */
    @Resource("{zoneId}")
    class Id(
        val parent: Zones,
        val zoneId: String,
    ) {

        /**
         * Get the zone managed by a server
         *
         * This class represents an Id resource with the specified parameters. It is used in conjunction with the Zones class.
         * An Id resource contains information about an identifier associated with a zone.
         *
         * Responses:
         * * 200 OK – A Zone Returns: [models.pdns.Zone] object
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
         *
         * @param parent The Zones.Id instance that this Get resource belongs to.
         * @param rrsets whether to include the “rrsets” in the response Zone object.
         * @param rrsetName Limit output to RRsets for this name.
         * @param rrsetType Limit output to the RRset of this type. Can only be used together with rrsetName.
         */
        @Resource("")
        class Get(
            val parent: Zones.Id,
            val rrsets: Boolean? = null,
            @SerialName("rrset_name") val rrsetName: String? = null,
            @SerialName("rrset_type") val rrsetType: String? = null
        ) {
            init {
                require(rrsetName == null || rrsetType != null) {
                    "rrsetName must be specified when rrsetType is specified"
                }
            }
        }

        /**
         * Deletes this zone, all attached metadata and rrsets.
         *
         * Responses:
         * * 204 No Content – Returns 204 No Content on success.
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
         *
         * @property parent The parent [Zones.Id] instance.
         */
        @Resource("")
        class Delete(val parent: Id)

        /**
         * Creates/modifies/deletes RRsets present in the payload and their comments. Returns 204 No Content on success.
         *
         * Responses:
         * * 204 No Content – Returns 204 No Content on success.
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
         *
         * @property parent The [Zones.Id] instance.
         */
        @Resource("")
        class Patch(val parent: Id)

        /**
         * Modifies basic zone data.
         *
         * The only fields in the zone structure which can be modified are:
         * kind, masters, catalog, account, soa_edit, soa_edit_api, api_rectify, dnssec, and nsec3param.
         * All other fields are ignored.
         *
         * Responses:
         * * 204 No Content – Returns 204 No Content on success.
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: Error object
         *
         * @property parent The [Zones.Id] instance.
         */
        @Resource("")
        class Put(val parent: Id)

        /**
         * Retrieve slave zone from its master.
         *
         * Fails when zone kind is not Slave, or slave is disabled in the configuration. Clients MUST NOT send a body.
         *
         * Responses:
         * * 200 OK – OK
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
         *
         * @param parent The [Zones.Id] instance.
         */
        @Resource("axfr-retrieve")
        class AxfrRetrieve(val parent: Id)

        /**
         * Send a DNS NOTIFY to all slaves.
         *
         * Fails when zone kind is not Master or Slave, or master and slave are disabled in the configuration. Only works for Slave if renotify is on. Clients MUST NOT send a body.
         *
         * Responses:
         * * 200 OK – OK
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
         *
         * @param parent The [Zones.Id] instance.
         */
        @Resource("notify")
        class Notify(val parent: Id)

        /**
         * Returns the zone in AXFR format.
         *
         * Responses:
         * * 200 OK – OK Returns: string
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
         *
         * @param parent The [Zones.Id] instance.
         */
        class Export(val parent: Id)

        /**
         * Rectify the zone data.
         *
         * This does not take into account the API-RECTIFY metadata. Fails on slave zones and zones that do not have DNSSEC.
         *
         * Responses:
         * * 200 OK – OK Returns: string
         * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
         * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
         * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
         * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
         *
         * @param parent The [Zones.Id] instance.
         */
        @Resource("rectify")
        class Rectify(val parent: Id)
    }
}