package mdsadiqueinam.github.io.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import mdsadiqueinam.github.io.routes.authentication
import mdsadiqueinam.github.io.routes.zones

fun Application.configureRouting() {
    routing {
        authentication()
        zones()
    }
}
