package models

import kotlinx.serialization.Serializable

interface ApiResponseError<T> {
    val statusCode: Int
    val message: String
    val code: String
    val errors: List<T>?
}

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
    data class Success<T>(val data: T? = null, override val message: String) : ApiResponse()

    @Serializable
    data class Error<T>(
        override val statusCode: Int,
        override val message: String,
        override val code: String,
        override val errors: List<T>? = null
    ) : ApiResponse(), ApiResponseError<T>
}