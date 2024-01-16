package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a cryptographic key used in DNSSEC.
 *
 * @property type The type of the cryptokey. Set to “Cryptokey”.
 * @property id The internal identifier of the cryptokey. Read-only.
 * @property keyType The type of the key.
 * @property active Whether the key is in active use.
 * @property published Whether the DNSKEY record is published in the zone.
 * @property dnskey The DNSKEY record for this key.
 * @property ds An array of DS records for this key.
 * @property cds An array of DS records for this key, filtered by CDS publication settings.
 * @property privateKey The private key in ISC format.
 * @property algorithm The name of the algorithm for the key.
 * @property bits The size of the key.
 */
@Serializable
data class Cryptokey(
    /**
     * set to “Cryptokey”
     */
    val type: String,

    /**
     * The internal identifier, read only
     */
    val id: String,

    @SerialName("keytype") val keyType: String,

    /**
     * Whether the key is in active use
     */
    val active: Boolean,

    /**
     * Whether the DNSKEY record is published in the zone
     */
    val published: Boolean,

    /**
     * The DNSKEY record for this key
     */
    val dnskey: String,

    /**
     * An array of DS records for this key
     */
    val ds: String,

    /**
     * An array of DS records for this key, filtered by CDS publication settings
     */
    val cds: String,

    /**
     * The private key in ISC format
     */
    @SerialName("privateKey") val privateKey: String,

    /**
     * The name of the algorithm for the key should be a mnemonic
     */
    val algorithm: String,

    /**
     * The size of the key
     */
    val bits: Int,
)
