package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Enumeration representing different kinds of zones.
 * Each ZoneKind has a corresponding type.
 */
@Serializable
enum class ZoneKind {
    @SerialName("Native") NATIVE,
    @SerialName("Master") MASTER,
    @SerialName("Slave") SLAVE,
    @SerialName("Producer") PRODUCER,
    @SerialName("Consumer") CONSUMER
}

@Serializable
enum class SOAEditApi {
    DEFAULT,
    INCREASE,
    EPOCH,
    @SerialName("") OFF
}

/**
 * Represents a zone to be created.
 *
 * @property name Name of the zone (e.g. 'example.com.') MUST have a trailing dot
 * @property type Set to 'Zone'
 * @property kind Zone kind, one of [ZoneKind]
 * @property nameservers Simple list of nameserver names, including the trailing dot.
 *                      Not required for slave zones.
 * @property masters List of IP addresses configured as a master for this zone ('Slave' type zones only)
 * @property dnssec Whether this zone is DNSSEC signed (inferred from presigned being true XOR presence of at least one cryptokey with active being true)
 * @property nsec3narrow Whether the zone uses NSEC3 narrow
 * @property soaEditApi The SOA-EDIT-API metadata item
 * @property apiRectify Whether the zone will be rectified on data changes via the API
 * @property zone MAY contain a BIND-style zone file when creating a zone
 * @property catalog The catalog this zone is a member of
 * @property account MAY be set. Its value is defined by local policy
 * @property masterTsigKeyIds The id of the TSIG keys used for master operation in this zone
 * @property slaveTsigKeyIds The id of the TSIG keys used for slave operation in this zone
 */
@Serializable
data class CreateZone(
    val name: String,
    val type: String = "Zone",
    val kind: ZoneKind = ZoneKind.NATIVE,
    val nameservers: List<String> = emptyList(),
    val masters: List<String> = emptyList(),
    val dnssec: Boolean = true,
    val nsec3narrow: Boolean = false,
    @SerialName("soa_edit_api") val soaEditApi: SOAEditApi = SOAEditApi.DEFAULT,
    @SerialName("api_rectify") val apiRectify: Boolean = false,
    val zone: String? = null,
    val catalog: String? = null,
    val account: String? = null,
    @SerialName("master_tsig_key_ids") val masterTsigKeyIds: List<String>? = null,
    @SerialName("slave_tsig_key_ids") val slaveTsigKeyIds: List<String>? = null,
)

/**
 * Represents a DNS zone.
 *
 * @property id The application should not interpret opaque zone id (string), assigned by the server.
 * Guaranteed to be safe for embedding in URLs.
 * @property name Name of the zone (e.g. 'example.com.') MUST have a trailing dot
 * @property type Set to 'Zone'
 * @property url API endpoint for this zone
 * @property kind Zone kind, one of 'Native', 'Master', 'Slave', 'Producer', 'Consumer'
 * @property rrsets RRSets in this zone (for zones/{zone_id} endpoint only; omitted during GET on the …/zones list endpoint)
 * @property serial The SOA serial number
 * @property notifiedSerial The SOA serial notifications have been sent out for
 * @property editedSerial The SOA serial as seen in query responses.
 * Calculated using the SOA-EDIT metadata, default-soa-edit and default-soa-edit-signed settings
 * @property masters List of IP addresses configured as a master for this zone ('Slave' type zones only)
 * @property dnssec Whether this zone is DNSSEC signed
 * (inferred from presigned being true XOR presence of at least one cryptokey with active being true)
 * @property nsec3param The NSEC3PARAM record
 * @property nsec3narrow Whether the zone uses NSEC3 narrow
 * @property presigned Whether the zone is pre-signed
 * @property soaEdit The SOA-EDIT metadata item
 * @property soaEditApi The SOA-EDIT-API metadata item
 * @property apiRectify Whether the zone will be rectified on data changes via the API
 * @property zone MAY contain a BIND-style zone file when creating a zone
 * @property catalog The catalog this zone is a member of
 * @property account MAY be set. Its value is defined by local policy
 * @property nameservers MAY be sent in client bodies during creation, and MUST NOT be sent by the server.
 * Simple list of nameserver names, including the trailing dot.
 * Not required for slave zones.
 * @property masterTsigKeyIds The id of the TSIG keys used for master operation in this zone
 * @property slaveTsigKeyIds The id of the TSIG keys used for slave operation in this zone
 */
@Serializable
data class Zone(
    val id: String,
    val name: String,
    val url: String,
    val kind: ZoneKind,
    val rrsets: List<RRSet>? = null,
    val serial: Int,
    @SerialName("notified_serial") val notifiedSerial: Int? = null,
    @SerialName("edited_serial") val editedSerial: Int,
    val masters: List<String>,
    val dnssec: Boolean,
    val nsec3param: String? = null,
    val nsec3narrow: Boolean? = null,
    val presigned: Boolean? = null,
    @SerialName("soa_edit") val soaEdit: String? = null,
    @SerialName("soa_edit_api") val soaEditApi: SOAEditApi? = null,
    @SerialName("api_rectify") val apiRectify: Boolean? = null,
    val zone: String? = null,
    val catalog: String? = null,
    val account: String,
    val nameservers: List<String>? = null,
    @SerialName("master_tsig_key_ids") val masterTsigKeyIds: List<String>? = null,
    @SerialName("slave_tsig_key_ids") val slaveTsigKeyIds: List<String>? = null,
)

@Serializable
data class PatchRRSets(
    val rrsets: List<RRSet>
)

enum class RRSetType {
    A,
    AAAA,
    AFSDB,
    ALIAS,
    APL,
    CAA,
    CDNSKEY,
    CDS,
    CERT,
    CNAME,
    DHCID,
    DLV,
    DNAME,
    DNSKEY,
    DS,
    HINFO,
    IPSECKEY,
    KEY,
    KX,
    LOC,
    MX,
    NAPTR,
    NS,
    NSEC,
    NSEC3,
    NSEC3PARAM,
    PTR,
    RRSIG,
    RP,
    SIG,
    SOA,
    SPF,
    SRV,
    SSHFP,
    TA,
    TKEY,
    TLSA,
    TSIG,
    TXT,
    URI,
}

enum class ChangeType {
    REPLACE,
    DELETE
}

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
    val name: String,
    val type: RRSetType,
    val ttl: Int,
    val changetype: ChangeType? = null,
    val records: List<Record>,
    val comments: List<Comment>,
)

/**
 * Represents a record with its content and disabled status.
 *
 * @property content The content of this record.
 * @property disabled Whether this record is disabled. When unset, the record is not disabled.
 */
@Serializable
data class Record(
    val content: String,
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
    val content: String,
    val account: String,
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
    val kind: String,
    val metadata: List<String>
)