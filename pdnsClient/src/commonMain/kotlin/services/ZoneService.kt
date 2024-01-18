package services

import extensions.process
import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import models.Zone
import resources.Zones

class ZoneService(private val client: HttpClient) {
    suspend fun fetchZones(serverId: String, zone: String? = null, dnssec: Boolean? = null): Result<List<Zone>> {
        val response = client.get(Zones.Get(serverId, zone, dnssec))
        return response.process()
    }
}