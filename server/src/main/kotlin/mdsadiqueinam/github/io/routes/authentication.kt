package mdsadiqueinam.github.io.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Routing
import mdsadiqueinam.github.io.repositories.AuthenticationRepository
import models.RegisterBody
import org.koin.ktor.ext.inject
import recources.Register

fun Routing.authentication() {
    val authenticationRepository by inject<AuthenticationRepository>()
    post<Register> {
        val body = call.receive<RegisterBody>()
        val response = authenticationRepository.register(body)
        call.respond(response)
    }
}