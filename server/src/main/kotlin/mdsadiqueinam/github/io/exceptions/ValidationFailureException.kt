package mdsadiqueinam.github.io.exceptions

import io.ktor.http.*
import kotlinx.serialization.Serializable
import mdsadiqueinam.github.io.customPlugins.requestValidation.ValidatedField

@Serializable
data class ValidationFailureException(
    val fields: List<ValidatedField>
) : ApiException(HttpStatusCode.UnprocessableEntity, "Validation failed", "E_VALIDATION_FAILED")