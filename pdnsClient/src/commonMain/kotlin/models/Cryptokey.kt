package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An enumeration representing the type of cryptographic key.
 */
@Serializable
enum class CryptokeyType {
    @SerialName("ksk") KSK,
    @SerialName("zsk") ZSK,
    @SerialName("csk") CSK,
}

/**
 * Represents a cryptographic key used for secure communication.
 *
 * @property type The type of the cryptokey. The Default value is "Cryptokey".
 * @property id The unique identifier of the cryptokey.
 * @property keyType The type of the cryptokey.
 * @property active Indicates whether the cryptokey is active.
 * @property published Indicates whether the cryptokey is published.
 * @property dnskey The DNSKEY value of the cryptokey.
 * @property ds The list of DS (Delegation Signer) values associated with the cryptokey.
 * @property cds The list of CDS (Child Delegation Signer) values associated with the cryptokey, nullable.
 * @property privateKey The private key of the cryptokey, nullable.
 * @property algorithm The algorithm used for the cryptokey.
 * @property bits The size in bits of the cryptokey.
 */
@Serializable
data class Cryptokey(
    val type: String = "Cryptokey",
    val id: Int,
    @SerialName("keytype") val keyType: CryptokeyType,
    val active: Boolean,
    val published: Boolean,
    val dnskey: String,
    val ds: List<String>,
    val cds: List<String>? = null,
    @SerialName("privateKey") val privateKey: String? = null,
    val algorithm: String,
    val bits: Int,
)

/**
 * Represents the body of a cryptographic key.
 *
 * @property keyType The type of the cryptographic key.
 * @property active Whether the cryptographic key is active.
 * @property published Whether the cryptographic key is published.
 * @property dnskey The DNS key associated with the cryptographic key.
 * @property ds The list of DS (Delegation Signer) records associated with the cryptographic key.
 * @property cds The list of CDS (Child Delegation Signer) records associated with the cryptographic key.
 * @property privateKey The private key associated with the cryptographic key.
 * @property algorithm The algorithm used for the cryptographic key.
 * @property bits The number of bits in the cryptographic key.
 */
@Serializable
data class CryptokeyBody(
    @SerialName("keytype") val keyType: CryptokeyType? = null,
    val active: Boolean? = null,
    val published: Boolean? = null,
    val dnskey: String? = null,
    val ds: List<String>? = null,
    val cds: List<String>? = null,
    @SerialName("privateKey") val privateKey: String? = null,
    val algorithm: String? = null,
    val bits: Int? = null,
)