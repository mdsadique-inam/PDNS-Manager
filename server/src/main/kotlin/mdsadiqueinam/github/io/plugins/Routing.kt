package mdsadiqueinam.github.io.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.routing
import recources.Greeting

fun Application.configureRouting() {
    routing {
        get<Greeting> {
            this.call.respond(Greeting.Body("Hello, ${it.name}!"))
        }
    }
}
