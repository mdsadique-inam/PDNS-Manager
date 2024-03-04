package mdsadiqueinam.github.io.exceptions

import io.ktor.http.*
import kotlinx.serialization.Serializable
import models.ValidatedField

@Serializable
data class ValidationFailureException(
    val errors: List<ValidatedField>
) : ApiException(HttpStatusCode.UnprocessableEntity, "Validation failed", "E_VALIDATION_FAILED")