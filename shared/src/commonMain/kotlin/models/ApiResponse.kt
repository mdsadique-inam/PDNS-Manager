package models

import kotlinx.serialization.Serializable

@Serializable
data class ValidatedField(
    val field: String,
    val errors: List<String>
) {
    override fun toString(): String {
        return "field '$field' has following errors ${errors.joinToString(", ")}"
    }
}

@Serializable
sealed class ApiResponse {
    abstract val message: String

    @Serializable
    data class Success<T>(val data: T, override val message: String) : ApiResponse()

    @Serializable
    data class Error<T>(val statusCode: Int, override val message: String, val code: String, val errors: T? = null) : ApiResponse()
}