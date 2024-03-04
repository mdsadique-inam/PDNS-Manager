package mdsadiqueinam.github.io

import io.ktor.server.application.*
import mdsadiqueinam.github.io.plugins.*

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    configureDI()
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureSerialization()
    configureRequestValidation()
    configureExceptionHandler()
    configureRouting()
}
