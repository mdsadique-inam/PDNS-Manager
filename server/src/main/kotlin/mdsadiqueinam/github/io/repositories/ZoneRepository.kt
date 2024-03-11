package mdsadiqueinam.github.io.repositories

import models.*
import org.koin.core.annotation.Single
import resources.Zones
import services.ZoneService

@Single
class ZoneRepository(private val zoneService: ZoneService) {

    suspend fun fetchZones(user: User, zones: Zones): List<Zone> {
        return zoneService.fetchZones(zones).getOrThrow().filter {
            it.account == user.id
        }
    }

    suspend fun createZone(user: User, body: ZoneBody, zones: Zones): Zone {
        val result = zoneService.createZone(zones, body.copy(account = user.id))
        return result.getOrThrow()
    }

    suspend fun fetchZone(
        user: User,
        zonesId: Zones.Id,
    ): Zone {
        val zone = zoneService.fetchZone(zonesId).getOrThrow()
        require(zone.account == user.id) { "Zone does not belong to user" }
        return zone
    }

    suspend fun updateZone(user: User, zonesId: Zones.Id, zone: ZoneBody) {
        // Check if the zone belongs to the user
        fetchZone(user, zonesId)
        return zoneService.updateZone(zonesId, zone).getOrThrow()
    }

    suspend fun deleteZone(user: User,  zonesId: Zones.Id) {
        // Check if the zone belongs to the user
        fetchZone(user, zonesId)
        return zoneService.deleteZone(zonesId).getOrThrow()
    }

    suspend fun addRRSet(user: User, zonesId: Zones.Id, rrset: RRSet) {
        // Check if the zone belongs to the user
        fetchZone(user, zonesId)
        return zoneService.patchRRSets(zonesId, listOf(rrset.copy(changetype = null))).getOrThrow()
    }

    suspend fun updateRRSet(user: User, zonesId: Zones.Id, rrset: RRSet) {
        // Check if the zone belongs to the user
        fetchZone(user, zonesId)
        return zoneService.patchRRSets(zonesId, listOf(rrset.copy(changetype = ChangeType.REPLACE)))
            .getOrThrow()
    }

    suspend fun deleteRRSet(user: User, zonesId: Zones.Id, rrset: RRSet) {
        // Check if the zone belongs to the user
        fetchZone(user, zonesId)
        return zoneService.patchRRSets(zonesId, listOf(rrset.copy(changetype = ChangeType.DELETE)))
            .getOrThrow()
    }
}