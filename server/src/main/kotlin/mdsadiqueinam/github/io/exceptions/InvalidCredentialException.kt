package mdsadiqueinam.github.io.exceptions

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
class InvalidCredentialException : ApiException(HttpStatusCode.Unauthorized, "Invalid credentials", "E_INVALID_CREDENTIAL")