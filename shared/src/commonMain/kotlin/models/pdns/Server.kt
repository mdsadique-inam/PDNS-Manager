package models.pdns

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
    /**
     * Set to “Server”
     */
    val type: String,

    /**
     * The id of the server, “localhost”
     */
    val id: String,

    /**
     * “recursor” for the PowerDNS Recursor and “authoritative” for the Authoritative Server
     */
    @SerialName("daemon_type") val daemonType: String,

    /**
     * The version of the server software
     */
    val version: String,

    /**
     * The API endpoint for this server
     */
    val url: String,

    /**
     * The API endpoint for this server’s configuration
     */
    @SerialName("config_url") val configUrl: String,

    /**
     * The API endpoint for this server’s zones
     */
    @SerialName("zones_url") val zonesUrl: String,
)
