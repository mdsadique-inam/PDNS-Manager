package mdsadiqueinam.github.io.routes

import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import mdsadiqueinam.github.io.plugins.UserSession
import mdsadiqueinam.github.io.repositories.AuthenticationRepository
import models.ApiResponse
import models.LoginBody
import models.RegisterBody
import org.koin.ktor.ext.inject
import recources.Login
import recources.Register

fun Routing.authentication() {
    val authenticationRepository by inject<AuthenticationRepository>()
    post<Register> {
        val body = call.receive<RegisterBody>()
        val response = authenticationRepository.register(body)
        call.sessions.set(UserSession(response.token))
        call.respond(ApiResponse.Success(response, "User registered successfully"))
    }
    post<Login> {
        val body = call.receive<LoginBody>()
        val response = authenticationRepository.attempt(body.uid, body.password)
        call.sessions.set(UserSession(response.token))
        call.respond(ApiResponse.Success(response, "User logged in successfully"))
    }
}