package models.pdns

import kotlinx.serialization.Serializable

@Serializable
data class Cryptokey(
    val type: String,
    val id: String,
    val keytype: String,
    val active: Boolean,
    val published: Boolean,
    val dnskey: String,
    val ds: String,
    val cds: String,
    val privatekey: String,
    val algorithm: String,
    val bits: Int,
)
