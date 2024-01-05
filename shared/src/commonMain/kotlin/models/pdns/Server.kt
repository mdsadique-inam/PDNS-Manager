package models.pdns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Server(
    val type: String,
    val id: String,
    @SerialName("daemon_type") val daemonType: String,
    val version: String,
    val url: String,
    @SerialName("config_url") val configUrl: String,
    @SerialName("zones_url") val zonesUrl: String,
)
