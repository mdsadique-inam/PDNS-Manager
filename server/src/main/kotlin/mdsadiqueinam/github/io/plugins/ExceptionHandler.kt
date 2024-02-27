package mdsadiqueinam.github.io.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import mdsadiqueinam.github.io.exceptions.HttpException

fun Application.configureExceptionHandler() {
    install(StatusPages) {
        exception<Throwable> {  call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                HttpException(HttpStatusCode.InternalServerError, cause.localizedMessage, "E_INTERNAL_SERVER_ERROR")
            )
        }
        exception<HttpException> { call, cause ->
            call.respond(cause.statusCode, cause)
        }
    }
}