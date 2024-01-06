package mdsadiqueinam.github.io

import io.ktor.server.application.*
import mdsadiqueinam.github.io.plugins.*
import org.koin.ktor.plugin.koin

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.module() {
    koin {    }
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureSerialization()
    configureRequestValidation()
    configureRouting()
}
