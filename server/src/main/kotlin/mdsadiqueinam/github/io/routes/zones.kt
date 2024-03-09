package mdsadiqueinam.github.io.routes

import io.ktor.server.routing.*
import mdsadiqueinam.github.io.repositories.ZoneRepository
import org.koin.ktor.ext.inject

fun Route.zones() {
    val zoneRepository by inject<ZoneRepository>()
}