package mdsadiqueinam.github.io.exceptions

import io.ktor.http.*
import kotlinx.serialization.Serializable
import models.ApiResponseError
import models.ValidatedField

@Serializable
data class ValidationFailureException(
    override val errors: List<ValidatedField>,
    override val message: String = "Validation failed",
) : Throwable(message), ApiResponseError<ValidatedField> {
    override val statusCode: Int = HttpStatusCode.UnprocessableEntity.value
    override val code: String = "E_VALIDATION_FAILED"
}