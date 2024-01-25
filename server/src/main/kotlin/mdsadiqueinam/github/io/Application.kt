package mdsadiqueinam.github.io

import io.ktor.server.application.*
import mdsadiqueinam.github.io.plugins.*
import org.koin.ksp.generated.defaultModule
import org.koin.ktor.plugin.koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.module() {
    koin {
        slf4jLogger()
        defaultModule()
    }
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureSerialization()
    configureRequestValidation()
    configureRouting()
}
