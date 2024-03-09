package mdsadiqueinam.github.io.routes

import io.ktor.server.application.call
import io.ktor.server.auth.principal
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import mdsadiqueinam.github.io.plugins.JWTUserPrincipal
import mdsadiqueinam.github.io.repositories.ZoneRepository
import org.koin.ktor.ext.inject
import resources.Zones

fun Route.zones() {
    val zoneRepository by inject<ZoneRepository>()
    get<Zones> {
        val principal = call.principal<JWTUserPrincipal>()
        val zones = zoneRepository.fetchZones(principal!!.user, it.parent.serverId, it.zone, it.dnssec)
        call.respond(zones)
    }
    post<Zones> {
        val principal = call.principal<JWTUserPrincipal>()
//        val zone = zoneRepository.createZone(principal!!.user, it.parent.serverId, it.body, it.rrsets)
//        call.respond(zone)
    }
}