package services

import extensions.process
import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import models.Zone
import resources.Zones

class ZoneService(private val client: HttpClient) {
    suspend fun fetchZones(serverId: String, zone: String? = null, dnssec: Boolean? = null): Result<List<Zone>> = runCatching {
        val response = client.get(Zones.Get(serverId, zone, dnssec))
        return response.process()
    }

    suspend fun createZone(serverId: String, zone: Zone, rrsets: Boolean? = null): Result<Zone> = runCatching {
        val response = client.post(Zones.Post(serverId, rrsets)) {
            setBody(zone)
        }
        return response.process()
    }
}