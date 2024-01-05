package mdsadiqueinam.github.io.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureSecurity() {
    authentication {
        bearer {

        }
    }
}
