package mdsadiqueinam.github.io.plugins

import io.ktor.server.application.*
import mdsadiqueinam.github.io.database.DatabaseModule
import mdsadiqueinam.github.io.extensions.jwtConfig
import mdsadiqueinam.github.io.repositories.RepositoryModule
import mdsadiqueinam.github.io.util.createDelegatingPasswordEncoder
import org.koin.dsl.module
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.koin
import org.koin.logger.slf4jLogger

fun Application.configureDI() {
    koin {
        slf4jLogger()
        modules(DatabaseModule().module, RepositoryModule().module, module {
            single { createDelegatingPasswordEncoder() }
            factory { jwtConfig }
        })
    }
}
