package mdsadiqueinam.github.io.repositories

import models.*
import org.koin.core.annotation.Single
import services.ZoneService

@Single
class ZoneRepository(private val zoneService: ZoneService) {

    suspend fun fetchZones(user: User, zone: String? = null, dnssec: Boolean? = null): List<Zone> {
        return zoneService.fetchZones("localhost", zone, dnssec).getOrThrow().filter {
            it.account == user.id
        }
    }

    suspend fun createZone(user: User, body: ZoneBody, rrsets: Boolean? = null): Zone {
        val result = zoneService.createZone("localhost", body.copy(account = user.id), rrsets)
        return result.getOrThrow()
    }

    suspend fun fetchZone(
        user: User,
        zoneId: String,
        rrsets: Boolean? = null,
        rrsetName: String? = null,
        rrsetType: String? = null
    ): Zone {
        val zone = zoneService.fetchZone("localhost", zoneId, rrsets, rrsetName, rrsetType).getOrThrow()
        require(zone.account == user.id) { "Zone does not belong to user" }
        return zone
    }

    suspend fun updateZone(user: User, zoneId: String, zone: ZoneBody) {
        // Check if the zone belongs to the user
        fetchZone(user, zoneId)
        return zoneService.updateZone("localhost", zoneId, zone).getOrThrow()
    }

    suspend fun deleteZone(user: User, zoneId: String) {
        // Check if the zone belongs to the user
        fetchZone(user, zoneId)
        return zoneService.deleteZone("localhost", zoneId).getOrThrow()
    }

    suspend fun addRRSet(user: User, zoneId: String, rrset: RRSet) {
        // Check if the zone belongs to the user
        fetchZone(user, zoneId)
        return zoneService.patchRRSets("localhost", zoneId, listOf(rrset.copy(changetype = null))).getOrThrow()
    }

    suspend fun updateRRSet(user: User, zoneId: String, rrset: RRSet) {
        // Check if the zone belongs to the user
        fetchZone(user, zoneId)
        return zoneService.patchRRSets("localhost", zoneId, listOf(rrset.copy(changetype = ChangeType.REPLACE))).getOrThrow()
    }

    suspend fun deleteRRSet(user: User, zoneId: String, rrset: RRSet) {
        // Check if the zone belongs to the user
        fetchZone(user, zoneId)
        return zoneService.patchRRSets("localhost", zoneId, listOf(rrset.copy(changetype = ChangeType.DELETE))).getOrThrow()
    }
}