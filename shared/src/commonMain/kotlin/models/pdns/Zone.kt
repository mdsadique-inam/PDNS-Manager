package models.pdns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a DNS zone.
 *
 * @property id Opaque zone id (string), assigned by the server, should not be interpreted by the application. Guaranteed to be safe for embedding in URLs.
 * @property name Name of the zone (e.g. “example.com.”) MUST have a trailing dot
 * @property type Set to “Zone”
 * @property url API endpoint for this zone
 * @property kind Zone kind, one of “Native”, “Master”, “Slave”, “Producer”, “Consumer”
 * @property rrsets RRSets in this zone (for zones/{zone_id} endpoint only; omitted during GET on the …/zones list endpoint)
 * @property serial The SOA serial number
 * @property notifiedSerial The SOA serial notifications have been sent out for
 * @property editedSerial The SOA serial as seen in query responses.
 * Calculated using the SOA-EDIT metadata, default-soa-edit and default-soa-edit-signed settings
 * @property masters List of IP addresses configured as a master for this zone (“Slave” type zones only)
 * @property dnssec Whether or not this zone is DNSSEC signed
 * (inferred from presigned being true XOR presence of at least one cryptokey with active being true)
 * @property nsec3param The NSEC3PARAM record
 * @property nsec3narrow Whether or not the zone uses NSEC3 narrow
 * @property presigned Whether or not the zone is pre-signed
 * @property soaEdit The SOA-EDIT metadata item
 * @property soaEditApi The SOA-EDIT-API metadata item
 * @property apiRectify Whether or not the zone will be rectified on data changes via the API
 * @property zone MAY contain a BIND-style zone file when creating a zone
 * @property catalog The catalog this zone is a member of
 * @property account MAY be set. Its value is defined by local policy
 * @property nameservers MAY be sent in client bodies during creation, and MUST NOT be sent by the server. Simple list of strings of nameserver names, including the trailing dot. Not required for slave zones.
 * @property masterTsigKeyIds The id of the TSIG keys used for master operation in this zone
 * @property slaveTsigKeyIds The id of the TSIG keys used for slave operation in this zone
 */
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

/**
 * Represents a DNS Resource Record Set (RRSet).
 *
 * @property name Name for the record set (e.g. "www.powerdns.com.")
 * @property type Type of this record (e.g. "A", "PTR", "MX"). Must be uppercase.
 * @property ttl DNS TTL of the records, in seconds. Must not be included when `changetype` is set to "DELETE".
 * @property changetype Must be added when updating the RRSet.
 * Must be REPLACE or DELETE.
 * * With DELETE, all existing RRs matching `name` and `type` will be deleted, including all comments.
 * * With REPLACE: when `records` are present, all existing RRs matching `name` and `type` will be deleted,
 *   and then new records given in `records` will be created.
 *   If no records are left, any existing comments will be deleted as well.
 * * When `comments` are present, all existing comments for the RRs matching `name` and `type` will be deleted,
 *   and then new comments given in `comments` will be created.
 * @property records All records in this RRSet.
 * When updating records, this is the list of new records (replacing the old ones).
 * Must be empty when `changetype` is set to DELETE.
 * An empty list results in deletion of all records (and comments).
 * @property comments List of comments.
 * Must be empty when `changetype` is set to DELETE.
 * An empty list results in deletion of all comments.
 */
@Serializable
data class RRSet(
    /**
     * Name for record set (e.g. “www.powerdns.com.”)
     */
    val name: String,

    /**
     * Type of this record (e.g. “A”, “PTR”, “MX”) MUST be uppercase
     */
    val type: String,

    /**
     * DNS TTL of the records, in seconds. MUST NOT be included when changetype is set to “DELETE”.
     */
    val ttl: Int,

    /**
     * MUST be added when updating the RRSet.
     * Must be REPLACE or DELETE.
     * With DELETE, all existing RRs matching name and type will be deleted, including all comments.
     * With REPLACE: when records is present, all existing RRs matching name and type will be deleted,
     * and then new records given in records will be created.
     * If no records are left, any existing comments will be deleted as well.
     * When comments is present, all existing comments for the RRs matching name and type will be deleted,
     * and then new comments given in comments will be created.
     */
    val changetype: String,

    /**
     * All records in this RRSet.
     * When updating Records, this is the list of new records (replacing the old ones).
     * Must be empty when changetype is set to DELETE.
     * An empty list results in deletion of all records (and comments).
     */
    val records: List<Record>,

    /**
     * List of Comment.
     * Must be empty when changetype is set to DELETE.
     * An empty list results in deletion of all comments.
     * modified_at is optional and defaults to the current server time.
     */
    val comments: List<Comment>,
)

/**
 * Represents a record with its content and disabled status.
 *
 * @property content The content of this record.
 * @property disabled Whether or not this record is disabled. When unset, the record is not disabled.
 */
@Serializable
data class Record(
    /**
     * The content of this record
     */
    val content: String,

    /**
     * Whether or not this record is disabled. When unset, the record is not disabled
     */
    val disabled: Boolean,
)

/**
 * Represents a comment with its content, account name, and last modified timestamp.
 *
 * @property content The actual comment.
 * @property account Name of the account that added the comment.
 * @property modifiedAt Timestamp of the last change to the comment.
 */
@Serializable
data class Comment(
    /**
     * The actual comment
     */
    val content: String,

    /**
     * Name of an account that added the comment
     */
    val account: String,

    /**
     * Timestamp of the last change to the comment
     */
    @SerialName("modified_at") val modifiedAt: Int
)

/**
 * Represents metadata associated with a certain kind.
 *
 * @property kind Name of the metadata.
 * @property metadata Array with all values for this metadata kind.
 */
@Serializable
data class Metadata(
    /**
     * Name of the metadata
     */
    val kind: String,

    /**
     * Array with all values for this metadata kind.
     */
    val metadata: List<String>
)