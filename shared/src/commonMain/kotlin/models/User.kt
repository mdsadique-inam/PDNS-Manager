package models

import kotlinx.serialization.Serializable
import serializers.SLocalDateTime

@Serializable
data class User(
    val id: String,
    val name: String,
    val username: String,
    val email: String,
    val createdAt: SLocalDateTime,
    val updatedAt: SLocalDateTime? = null,
)