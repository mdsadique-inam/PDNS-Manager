package models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterBody(
    val name: String,
    val username: String,
    val email: String,
    val password: String,
)


@Serializable
data class LoginResponse(
    val token: String,
    val refreshToken: String,
    val user: User
)