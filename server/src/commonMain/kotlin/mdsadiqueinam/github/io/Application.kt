package mdsadiqueinam.github.io

import io.ktor.server.application.*
import mdsadiqueinam.github.io.koin.plugin.koin
import mdsadiqueinam.github.io.plugins.*


fun Application.module() {
    koin {  }
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureSerialization()
    configureRequestValidation()
    configureRouting()
}
