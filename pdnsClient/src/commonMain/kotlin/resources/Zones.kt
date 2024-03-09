package resources

import io.ktor.resources.*
import kotlinx.serialization.SerialName

/**
 * Get: To retrieve the zone list
 * Post: To create a new zone
 */
@Resource("zones")
class Zones(
    val parent: Servers.Id, val zone: String? = null, val dnssec: Boolean? = null, val rrsets: Boolean? = null
) {
    constructor(
        serverId: String, zone: String? = null, dnssec: Boolean? = null, rrsets: Boolean? = null
    ) : this(Servers.Id(serverId), zone, dnssec, rrsets)


    /**
     * Get: To retrieve the zone details
     * Put: To update the zone details
     * Patch: To Creates/modifies/deletes RRsets in the zone
     * Delete: To delete the zone
     */
    @Resource("{zoneId}")
    class Id(
        val parent: Zones,
        val zoneId: String,
        val rrsets: Boolean? = null,
        @SerialName("rrset_name") val rrsetName: String? = null,
        @SerialName("rrset_type") val rrsetType: String? = null
    ) {

        constructor(
            serverId: String,
            zoneId: String,
            rrsets: Boolean? = null,
            rrsetName: String? = null,
            rrsetType: String? = null
        ) : this(Zones(Servers.Id(serverId)), zoneId, rrsets, rrsetName, rrsetType)

        init {
            require(rrsetName == null || rrsetType != null) {
                "rrsetName must be specified when rrsetType is specified"
            }
        }

        /**
         * Put: Retrieve slave zone from its master.
         */
        @Resource("axfr-retrieve")
        class AxfrRetrieve(val parent: Id) {
            constructor(serverId: String, zoneId: String) : this(Id(serverId, zoneId))
        }

        /**
         * Put: Send a DNS NOTIFY to all slaves.
         */
        @Resource("notify")
        class Notify(val parent: Id) {
            constructor(serverId: String, zoneId: String) : this(Id(serverId, zoneId))
        }

        /**
         * Get: To get the zone details in AXFR format
         */
        @Resource("export")
        class Export(val parent: Id) {
            constructor(serverId: String, zoneId: String) : this(Id(serverId, zoneId))
        }

        /**
         * Put: Rectify the zone data.
         */
        @Resource("rectify")
        class Rectify(val parent: Id) {
            constructor(serverId: String, zoneId: String) : this(Id(serverId, zoneId))
        }
    }
}