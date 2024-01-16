package models

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val error: String,
    val errors: List<String>? = null,
)
