package mdsadiqueinam.github.io.exceptions

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
class InvalidCredentialException : HttpException(HttpStatusCode.Unauthorized, "Invalid username or password", "E_INVALID_CREDENTIAL")