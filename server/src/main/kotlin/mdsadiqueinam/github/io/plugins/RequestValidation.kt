package mdsadiqueinam.github.io.plugins

import io.ktor.server.application.*
import mdsadiqueinam.github.io.customPlugins.requestValidation.RequestValidation
import mdsadiqueinam.github.io.validators.validateAuthentication


fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validateAuthentication()
    }
}
