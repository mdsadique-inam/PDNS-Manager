package mdsadiqueinam.github.io.routes

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.Routing
import recources.Register

fun Routing.auth() {
    post<Register> {
    }
}