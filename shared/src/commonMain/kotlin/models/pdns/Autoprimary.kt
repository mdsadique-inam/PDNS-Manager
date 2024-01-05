package models.pdns

import kotlinx.serialization.Serializable

@Serializable
data class Autoprimary(
    val id: String,
    val nameserver: String,
    val account: String,
)
