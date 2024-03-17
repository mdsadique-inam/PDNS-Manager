package mdsadiqueinam.github.io.routes

import io.ktor.server.application.call
import io.ktor.server.auth.principal
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import mdsadiqueinam.github.io.plugins.UserPrincipal
import models.ApiResponse
import recources.Account

fun Route.users() {
    get<Account> {
        val principal = call.principal<UserPrincipal>()
        call.respond(ApiResponse.Success(principal!!.user, "Current user retrieved successfully"))
    }
}