package models.pdns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val content: String,
    val disabled: Boolean,
    val name: String,
    @SerialName("object_type") val objectType: String,
    @SerialName("zone_id") val zoneId: String,
    val zone: String,
    val type: String,
    val ttl: Int,
)
