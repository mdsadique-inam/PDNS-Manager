package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a server in the PowerDNS system.
 *
 * @property type The type of the server. Set to "Server".
 * @property id The ID of the server. Defaults to "localhost".
 * @property daemonType The type of the server daemon. Possible values are "recursor" for the PowerDNS Recursor and "authoritative" for the Authoritative Server.
 * @property version The version of the server software.
 * @property url The API endpoint for this server.
 * @property configUrl The API endpoint for this server's configuration.
 * @property zonesUrl The API endpoint for this server's zones.
 */
@Serializable
data class Server(
    val type: String = "Server",
    val id: String,
    @SerialName("daemon_type") val daemonType: String,
    val version: String,
    val url: String,
    @SerialName("config_url") val configUrl: String,
    @SerialName("zones_url") val zonesUrl: String,
)

/**
 * Represents a primary server for the AutoPrimary feature.
 *
 * @property ip The IP address of the autoprimary server.
 * @property nameserver The DNS name of the autoprimary server.
 * @property account The account name for the autoprimary server.
 */
@Serializable
data class AutoPrimary(
    val ip: String,
    val nameserver: String,
    val account: String,
)

@Serializable
enum class SearchType {
    @SerialName("all")
    ALL,
    @SerialName("record")
    RECORD,
    @SerialName("zone")
    ZONE,
    @SerialName("comment")
    COMMENT,
}

@Serializable
data class SearchResult(
    val content: String? = null,
    val disabled: Boolean? = null,
    val name: String? = null,
    @SerialName("object_type") val objectType: SearchType,
    @SerialName("zone_id") val zoneId: String? = null,
    val zone: String? = null,
    val type: String? = null,
    val ttl: Int? = null,
)

@Serializable
data class CacheFlushResult(
    val count: Int,
    val result: String,
)