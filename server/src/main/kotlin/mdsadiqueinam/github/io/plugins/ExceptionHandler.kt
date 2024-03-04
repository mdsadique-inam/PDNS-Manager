package mdsadiqueinam.github.io.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import mdsadiqueinam.github.io.customPlugins.requestValidation.RequestValidationException
import mdsadiqueinam.github.io.exceptions.ApiException
import mdsadiqueinam.github.io.exceptions.ValidationFailureException

fun Application.configureExceptionHandler() {
    install(StatusPages) {
        exception<Throwable> {  call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ApiException(HttpStatusCode.InternalServerError, cause.localizedMessage, "E_INTERNAL_SERVER_ERROR")
            )
        }
        exception<ApiException> { call, cause ->
            call.respond(cause.statusCode, cause)
        }
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.UnprocessableEntity, ValidationFailureException(cause.fields))
        }
    }
}