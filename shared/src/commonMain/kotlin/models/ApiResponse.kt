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
sealed class ApiResponse<out S, out E> {
    abstract val message: String

    @Serializable
    data class Success<S>(val data: S? = null, override val message: String) : ApiResponse<S, Nothing>()

    @Serializable
    data class Error<E>(
        override val statusCode: Int,
        override val message: String,
        override val code: String,
        override val errors: List<E>? = null
    ) : ApiResponse<Nothing, E>(), ApiResponseError<E>
}