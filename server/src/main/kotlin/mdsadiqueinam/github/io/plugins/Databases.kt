package mdsadiqueinam.github.io.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val pgConfig = environment.config.config("database.pg")
    Database.connect(
        url = pgConfig.property("url").getString(),
        driver = pgConfig.property("driver").getString(),
        user = pgConfig.property("user").getString(),
        password = pgConfig.property("password").getString()
    )
}