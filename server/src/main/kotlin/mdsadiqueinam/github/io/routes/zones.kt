package mdsadiqueinam.github.io.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.patch
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import mdsadiqueinam.github.io.plugins.JWTUserPrincipal
import mdsadiqueinam.github.io.repositories.ZoneRepository
import mdsadiqueinam.github.io.resources.RRSets
import models.ApiResponse
import models.RRSet
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
    put<Zones.Id> {
        val principal = call.principal<JWTUserPrincipal>()
        val body = call.receive<ZoneBody>()
        zoneRepository.updateZone(principal!!.user, it, body)
        call.respond(HttpStatusCode.NoContent, ApiResponse.Success(null, "Zone updated successfully"))
    }
    delete<Zones.Id> {
        val principal = call.principal<JWTUserPrincipal>()
        zoneRepository.deleteZone(principal!!.user, it)
        call.respond(HttpStatusCode.NoContent, ApiResponse.Success(null, "Zone deleted successfully"))
    }
    patch<RRSets> {
        val principal = call.principal<JWTUserPrincipal>()
        val rrset = call.receive<RRSet>()
        when (it.action) {
            RRSets.RRSetsAction.ADD -> zoneRepository.addRRSet(principal!!.user, it.parent, rrset)
            RRSets.RRSetsAction.UPDATE -> zoneRepository.updateRRSet(principal!!.user, it.parent, rrset)
            RRSets.RRSetsAction.DELETE -> zoneRepository.deleteRRSet(principal!!.user, it.parent, rrset)
        }
        call.respond(HttpStatusCode.NoContent, ApiResponse.Success(null, "RRSet updated successfully"))
    }
}