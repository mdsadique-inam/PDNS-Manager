package mdsadiqueinam.github.io.exceptions

import kotlinx.serialization.Serializable
import mdsadiqueinam.github.io.serializers.SHttpStatusCode
import java.util.*

@Serializable
open class HttpException(val statusCode: SHttpStatusCode, override val message: String, val code: String) :
    Throwable(message) {

    constructor(
        statusCode: SHttpStatusCode,
        message: String,
    ) : this(statusCode, message, "E_${statusCode.description.uppercase(Locale.getDefault()).replace(" ", "_")}")
}