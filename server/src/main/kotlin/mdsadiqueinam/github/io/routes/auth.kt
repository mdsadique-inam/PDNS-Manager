package mdsadiqueinam.github.io.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Routing
import models.RegisterBody
import recources.Register

fun Routing.auth() {
    post<Register> {
        val body = call.receive<RegisterBody>()
        call.respond(body)
    }
}