package models.pdns

import kotlinx.serialization.Serializable

@Serializable
data class TSIGKey(
    val name: String,
    val id: String,
    val algorithm: String,
    val key: String,
    val type: String
)
