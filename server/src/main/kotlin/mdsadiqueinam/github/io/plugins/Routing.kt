package mdsadiqueinam.github.io.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.*
import mdsadiqueinam.github.io.routes.authentication
import mdsadiqueinam.github.io.routes.users
import mdsadiqueinam.github.io.routes.zones

fun Application.configureRouting() {
    routing {
        route("/api") {
            authentication()
            authenticate(AuthenticationType.SESSION, AuthenticationType.JWT) {
                users()
                zones()
            }
        }
    }
}
