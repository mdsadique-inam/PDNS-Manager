package models

import kotlinx.serialization.Serializable

/**
 * Represents a primary server for the AutoPrimary feature.
 *
 * @property ip The IP address of the autoprimary server.
 * @property nameserver The DNS name of the autoprimary server.
 * @property account The account name for the autoprimary server.
 */
@Serializable
data class AutoPrimary (
    /**
     * IP address of the autoprimary server
     */
    val ip: String,

    /**
     *  DNS name of the autoprimary server
     */
    val nameserver: String,

    /**
     * Account name for the autoprimary server
     */
    val account: String,
)
