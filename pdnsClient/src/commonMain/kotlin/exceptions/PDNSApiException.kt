package exceptions

import io.ktor.http.*
import models.Error

class PDNSApiException(val httpStatusCode: HttpStatusCode, val error: Error) : Throwable(error.error)