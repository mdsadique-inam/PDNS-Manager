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
        zoneId: String,
        rrsets: Boolean? = null,
        rrsetName: String? = null,
        rrsetType: String? = null
    ): Zone {
        return zoneService.fetchZone("localhost", zoneId, rrsets, rrsetName, rrsetType).getOrThrow()
    }

    suspend fun updateZone(zoneId: String, zone: ZoneBody) {
        return zoneService.updateZone("localhost", zoneId, zone).getOrThrow()
    }

    suspend fun deleteZone(zoneId: String) {
        return zoneService.deleteZone("localhost", zoneId).getOrThrow()
    }

    suspend fun addRRSet(zoneId: String, rrset: RRSet) {
        return zoneService.patchRRSets("localhost", zoneId, listOf(rrset.copy(changetype = null))).getOrThrow()
    }

    suspend fun updateRRSet(zoneId: String, rrset: RRSet) {
        return zoneService.patchRRSets("localhost", zoneId, listOf(rrset.copy(changetype = ChangeType.REPLACE))).getOrThrow()
    }

    suspend fun deleteRRSet(zoneId: String, rrset: RRSet) {
        return zoneService.patchRRSets("localhost", zoneId, listOf(rrset.copy(changetype = ChangeType.DELETE))).getOrThrow()
    }
}