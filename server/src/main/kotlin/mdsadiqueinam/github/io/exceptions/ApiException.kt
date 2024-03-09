package mdsadiqueinam.github.io.exceptions

import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import mdsadiqueinam.github.io.serializers.SHttpStatusCode
import models.ApiResponseError
import java.util.*

@Serializable
open class ApiException(
    @Transient val httpStatusCode: SHttpStatusCode = HttpStatusCode.InternalServerError,
    override val message: String,
    override val code: String,
    override val errors: List<String>? = null
) :
    Throwable(message), ApiResponseError<String> {

    override val statusCode: Int = httpStatusCode.value

    constructor(
        httpStatusCode: SHttpStatusCode,
        message: String,
    ) : this(
        httpStatusCode,
        message,
        "E_${httpStatusCode.description.uppercase(Locale.getDefault()).replace(" ", "_")}"
    )
}