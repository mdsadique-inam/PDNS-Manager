package mdsadiqueinam.github.io.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import mdsadiqueinam.github.io.repositories.UserRepository
import mdsadiqueinam.github.io.routes.auth
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    routing {
        val repository by inject<UserRepository>()
        post("/login") {
            repository.login("fnefjew", "skfjbsflvwulfy")
        }
        auth()
    }
}
