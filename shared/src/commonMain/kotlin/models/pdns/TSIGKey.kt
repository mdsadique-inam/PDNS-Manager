package models.pdns

import kotlinx.serialization.Serializable

/**
 * TSIGKey represents a TSIG key used for secure DNS transactions.
 *
 * @property name The name of the key.
 * @property id The ID for this key, used in the TSIGKey URL endpoint.
 * @property algorithm The algorithm of the TSIG key.
 * @property key The Base64 encoded secret key. It may be empty when listing keys or when generating the key material.
 * @property type Set to "TSIGKey".
 */
@Serializable
data class TSIGKey(
    /**
     * The name of the key
     */
    val name: String,

    /**
     * The ID for this key, used in the TSIGKey URL endpoint.
     */
    val id: String,

    /**
     * The algorithm of the TSIG key
     */
    val algorithm: String,

    /**
     * The Base64 encoded secret key, empty when listing keys.
     * Maybe empty when Posting to the server generate the key material
     */
    val key: String,

    /**
     * Set to “TSIGKey”
     */
    val type: String
)
