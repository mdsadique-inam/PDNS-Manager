package mdsadiqueinam.github.io.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val pgConfig = environment.config.config("database.pg")
    val datasource = HikariDataSource(HikariConfig().apply {
        jdbcUrl = pgConfig.property("url").getString()
        driverClassName = pgConfig.property("driver").getString()
        username = pgConfig.property("user").getString()
        password = pgConfig.property("password").getString()
        validate()
    })
    Database.connect(
        datasource = datasource,
    )
}

