package mdsadiqueinam.github.io

import io.ktor.server.application.*
import mdsadiqueinam.github.io.plugins.*
import mdsadiqueinam.github.io.util.createDelegatingPasswordEncoder
import org.koin.dsl.module
import org.koin.ksp.generated.defaultModule
import org.koin.ktor.plugin.koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    koin {
        slf4jLogger()
		modules(defaultModule, module {
			single { createDelegatingPasswordEncoder() }
		})
    }
    configureDatabases()
    configureHTTP()
    configureSecurity()
    configureSerialization()
    configureRequestValidation()
    configureRouting()
}
