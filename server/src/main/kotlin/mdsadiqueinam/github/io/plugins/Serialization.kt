package mdsadiqueinam.github.io.plugins

import io.ktor.serialization.kotlinx.json.json
import io.ktor.serialization.kotlinx.protobuf.*
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.resources.Resources
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
        protobuf()
    }
    install(Resources)
}
