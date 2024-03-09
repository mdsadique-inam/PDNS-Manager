package mdsadiqueinam.github.io.repositories

import models.*
import org.koin.core.annotation.Single
import services.ZoneService

@Single
class ZoneRepository(private val zoneService: ZoneService) {

    suspend fun fetchZones(user: User, serverId: String, zone: String? = null, dnssec: Boolean? = null): List<Zone> {
        return zoneService.fetchZones(serverId, zone, dnssec).getOrThrow().filter {
            it.account == user.id
        }
    }

    suspend fun createZone(user: User, serverId: String, body: ZoneBody, rrsets: Boolean? = null): Zone {
        val result = zoneService.createZone(serverId, body.copy(account = user.id), rrsets)
        return result.getOrThrow()
    }

    suspend fun fetchZone(
        user: User,
        serverId: String,
        zoneId: String,
        rrsets: Boolean? = null,
        rrsetName: String? = null,
        rrsetType: String? = null
    ): Zone {
        val zone = zoneService.fetchZone(serverId, zoneId, rrsets, rrsetName, rrsetType).getOrThrow()
        require(zone.account == user.id) { "Zone does not belong to user" }
        return zone
    }

    suspend fun updateZone(user: User, serverId: String, zoneId: String, zone: ZoneBody) {
        // Check if the zone belongs to the user
        fetchZone(user, serverId, zoneId)
        return zoneService.updateZone(serverId, zoneId, zone).getOrThrow()
    }

    suspend fun deleteZone(user: User, serverId: String, zoneId: String) {
        // Check if the zone belongs to the user
        fetchZone(user, serverId, zoneId)
        return zoneService.deleteZone(serverId, zoneId).getOrThrow()
    }

    suspend fun addRRSet(user: User, serverId: String, zoneId: String, rrset: RRSet) {
        // Check if the zone belongs to the user
        fetchZone(user, serverId, zoneId)
        return zoneService.patchRRSets(serverId, zoneId, listOf(rrset.copy(changetype = null))).getOrThrow()
    }

    suspend fun updateRRSet(user: User, serverId: String, zoneId: String, rrset: RRSet) {
        // Check if the zone belongs to the user
        fetchZone(user, serverId, zoneId)
        return zoneService.patchRRSets(serverId, zoneId, listOf(rrset.copy(changetype = ChangeType.REPLACE)))
            .getOrThrow()
    }

    suspend fun deleteRRSet(user: User, serverId: String, zoneId: String, rrset: RRSet) {
        // Check if the zone belongs to the user
        fetchZone(user, serverId, zoneId)
        return zoneService.patchRRSets(serverId, zoneId, listOf(rrset.copy(changetype = ChangeType.DELETE)))
            .getOrThrow()
    }
}