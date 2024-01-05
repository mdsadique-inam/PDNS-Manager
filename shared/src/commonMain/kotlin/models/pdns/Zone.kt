package models.pdns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Zone(
    val id: String,
    val name: String,
    val type: String,
    val url: String,
    val king: String,
    val rrsets: List<RRSet>,
    val serial: Int,
    @SerialName("notified_serial") val notifiedSerial: Int,
    @SerialName("edited_serial") val editedSerial: Int,
    val masters: List<String>,
    val dnssec: Boolean,
    val nsec3param: String,
    val nsec3narrow: Boolean,
    val presigned: Boolean,
    @SerialName("soa_edit") val soaEdit: String,
    @SerialName("soa_edit_api") val soaEditApi: String,
    @SerialName("api_rectify") val apiRectify: Boolean,
    val zone: String,
    val catalog: String,
    val account: String,
    val nameservers: List<String>,
    @SerialName("master_tsig_key_ids") val masterTsigKeyIds: List<String>,
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