package models.pdns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Zone(
    /**
     * Opaque zone id (string), assigned by the server, should not be interpreted by the application. Guaranteed to be safe for embedding in URLs.
     */
    val id: String,

    /**
     * Name of the zone (e.g. “example.com.”) MUST have a trailing dot
     */
    val name: String,

    /**
     *  Set to “Zone”
     */
    val type: String,

    /**
     * API endpoint for this zone
     */
    val url: String,

    /**
     * Zone kind, one of “Native”, “Master”, “Slave”, “Producer”, “Consumer”
     */
    val king: String,

    /**
     * RRSets in this zone (for zones/{zone_id} endpoint only; omitted during GET on the …/zones list endpoint)
     */
    val rrsets: List<RRSet>,

    /**
     *  The SOA serial number
     */
    val serial: Int,

    /**
     * The SOA serial notifications have been sent out for
     */
    @SerialName("notified_serial") val notifiedSerial: Int,

    /**
     * The SOA serial as seen in query responses.
     * Calculated using the SOA-EDIT metadata, default-soa-edit and default-soa-edit-signed settings
     */
    @SerialName("edited_serial") val editedSerial: Int,

    /**
     * List of IP addresses configured as a master for this zone (“Slave” type zones only)
     */
    val masters: List<String>,

    /**
     * Whether or not this zone is DNSSEC signed (inferred from presigned being true XOR presence of at least one cryptokey with active being true)
     */
    val dnssec: Boolean,

    /**
     * The NSEC3PARAM record
     */
    val nsec3param: String,

    /**
     * Whether or not the zone uses NSEC3 narrow
     */
    val nsec3narrow: Boolean,

    /**
     * Whether or not the zone is pre-signed
     */
    val presigned: Boolean,

    /**
     * The SOA-EDIT metadata item
     */
    @SerialName("soa_edit") val soaEdit: String,

    /**
     * The SOA-EDIT-API metadata item
     */
    @SerialName("soa_edit_api") val soaEditApi: String,

    /**
     * Whether or not the zone will be rectified on data changes via the API
     */
    @SerialName("api_rectify") val apiRectify: Boolean,

    /**
     * MAY contain a BIND-style zone file when creating a zone
     */
    val zone: String,

    /**
     * The catalog this zone is a member of
     */
    val catalog: String,

    /**
     * MAY be set. Its value is defined by local policy
     */
    val account: String,

    /**
     * MAY be sent in client bodies during creation, and MUST NOT be sent by the server.
     * Simple list of strings of nameserver names, including the trailing dot.
     * Not required for slave zones.
     */
    val nameservers: List<String>,

    /**
     * The id of the TSIG keys used for master operation in this zone
     */
    @SerialName("master_tsig_key_ids") val masterTsigKeyIds: List<String>,

    /**
     * The id of the TSIG keys used for slave operation in this zone
     */
    @SerialName("slave_tsig_key_ids") val slaveTsigKeyIds: List<String>,
)

@Serializable
data class RRSet(
    val name: String,
    val type: String,
    val ttl: Int,
    val changetype: String,
    val records: List<Record>,
    val comments: List<Comment>,
)

@Serializable
data class Record(
    val content: String,
    val disabled: Boolean,
)

@Serializable
data class Comment(
    val content: String,
    val account: String,
    @SerialName("modified_at")
    val modifiedAt: Int
)

@Serializable
data class Metadata(
    val kind: String,
    val metadata: List<String>
)