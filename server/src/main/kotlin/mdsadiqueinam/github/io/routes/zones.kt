package mdsadiqueinam.github.io.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import mdsadiqueinam.github.io.plugins.JWTUserPrincipal
import mdsadiqueinam.github.io.repositories.ZoneRepository
import models.ApiResponse
import models.ZoneBody
import org.koin.ktor.ext.inject
import resources.Zones

fun Route.zones() {
    val zoneRepository by inject<ZoneRepository>()
    get<Zones> {
        val principal = call.principal<JWTUserPrincipal>()
        val zones = zoneRepository.fetchZones(principal!!.user, it)
        call.respond(ApiResponse.Success(zones, "Zones retrieved successfully"))
    }
    post<Zones> {
        val principal = call.principal<JWTUserPrincipal>()
        val body = call.receive<ZoneBody>()
        val zone = zoneRepository.createZone(principal!!.user, body, it)
        call.respond(HttpStatusCode.Created, ApiResponse.Success(zone, "Zone created successfully"))
    }
    get<Zones.Id> {
        val principal = call.principal<JWTUserPrincipal>()
        val zone = zoneRepository.fetchZone(principal!!.user, it)
        call.respond(ApiResponse.Success(zone, "Zone retrieved successfully"))
    }
}