package models

import kotlinx.serialization.SerialName
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
    val name: String,
    val id: String,
    val algorithm: TSIGKeyAlgorithm,
    val key: String,
    val type: String = "TSIGKey"
)

@Serializable
data class TSIGKeyBody(
    val name: String? = null,
    val algorithm: TSIGKeyAlgorithm? = null,
    val key: String? = null,
    val type: String = "TSIGKey"
)

@Serializable
enum class TSIGKeyAlgorithm{
    @SerialName("hmac-md5") HMAC_MD5,
    @SerialName("hmac-sha1") HMAC_SHA1,
    @SerialName("hmac-sha224") HMAC_SHA224,
    @SerialName("hmac-sha256") HMAC_SHA256,
    @SerialName("hmac-sha384") HMAC_SHA384,
    @SerialName("hmac-sha512") HMAC_SHA512,
}