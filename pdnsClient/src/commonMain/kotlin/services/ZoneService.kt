package services

import extensions.process
import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import models.Zone
import resources.Zones

class ZoneService(private val client: HttpClient) {

    /**
     * List all Zones in a server.
     *
     * Responses:
     * * 200 OK – An array of Zones Returns: array of [models.Zone] objects
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server id
     * @param zone The zone name
     * @param dnssec Whether to include DNSSEC information
     * @return [Result] object with [List] of [Zone]s
     */
    suspend fun fetchZones(serverId: String, zone: String? = null, dnssec: Boolean? = null): Result<List<Zone>> =
        runCatching {
            val response = client.get(Zones(serverId, zone, dnssec))
            return response.process()
        }

    /**
     * Creates a new domain, returns the Zone on creation.
     *
     * Responses:
     * * 201 Created – A zone Returns: [models.Zone] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server id
     * @param zone The zone to create
     * @param rrsets Whether to include RRsets
     * @return [Result] object with [Zone]
     */
    suspend fun createZone(serverId: String, zone: Zone, rrsets: Boolean? = null): Result<Zone> = runCatching {
        val response = client.post(Zones(serverId = serverId, rrsets = rrsets)) {
            setBody(zone)
        }
        return response.process()
    }

    /**
     * Get the zone managed by a server
     *
     * This class represents an Id resource with the specified parameters.
     * It is used in conjunction with the Zones class.
     * An Id resource contains information about an identifier associated with a zone.
     *
     * Responses:
     * * 200 OK – A Zone Returns: [models.Zone] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server id
     * @param zoneId The zone id
     * @param rrsets whether to include the “rrsets” in the response Zone object.
     * @param rrsetName Limit output to RRSets for this name.
     * @param rrsetType Limit output to the RRSet of this type. Can only be used together with rrsetName.
     * @return [Result] object with [Zone]
     */
    suspend fun fetchZone(
        serverId: String,
        zoneId: String,
        rrsets: Boolean? = null,
        rrsetName: String? = null,
        rrsetType: String? = null
    ): Result<Zone> = runCatching {
        val response = client.get(Zones.Id(serverId, zoneId, rrsets, rrsetName, rrsetType))
        return response.process()
    }

    /**
     * Returns the zone in AXFR format.
     *
     * Responses:
     * * 200 OK – OK Returns: string
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server id
     * @param zoneId The zone id
     * @return [Result] object with AXFR [String]
     */
    suspend fun axfrZone(serverId: String, zoneId: String): Result<String> = runCatching {
        val response = client.get(Zones.Id.Export(serverId, zoneId))
        return response.process()
    }

    /**
     * Modifies basic zone data.
     *
     * The only fields in the zone structure which can be modified are:
     * kind, masters, catalog, account, soa_edit, soa_edit_api, api_rectify, dnssec, and nsec3param.
     * All other fields are ignored.
     *
     * Responses:
     * * 204 No Content – Returns 204 No Content on success.
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: Error object
     *
     * @param serverId The server id
     * @param zone The zone to update
     * @return [Result] object with [Unit]
     */
    suspend fun updateZone(serverId: String, zone: Zone): Result<Unit> = runCatching {
        val response = client.put(Zones.Id(serverId, zone.id)) {
            setBody(zone)
        }
        return response.process()
    }

    /**
     * Creates/modifies/deletes RRsets present in the payload and their comments. Returns 204 No Content on success.
     *
     * Responses:
     * * 204 No Content – Returns 204 No Content on success.
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server id
     * @param zone The zone to update, the zone id must be set and RRsets must be set
     */
    suspend fun patchRRSets(serverId: String, zone: Zone): Result<Unit> = runCatching {
        val response = client.patch(Zones.Id(serverId, zone.id)) {
            setBody(zone)
        }
        return response.process()
    }

    /**
     * Deletes this zone, all attached metadata and rrsets.
     *
     * Responses:
     * * 204 No Content – Returns 204 No Content on success.
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server id
     * @param zoneId The zone id
     * @return [Result] object with [Unit]
     */
    suspend fun deleteZone(serverId: String, zoneId: String): Result<Unit> = runCatching {
        val response = client.delete(Zones.Id(serverId, zoneId))
        return response.process()
    }

    /**
     * Retrieve slave zone from its master.
     *
     * Fails when zone kind is not Slave, or slave is disabled in the configuration. Clients MUST NOT send a body.
     *
     * Responses:
     * * 200 OK – OK
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server id
     * @param zoneId The zone id
     * @return [Result] object with [Unit]
     */
    suspend fun retrieveSlaveFromMaster(serverId: String, zoneId: String): Result<Unit> = runCatching {
        val response = client.put(Zones.Id.AxfrRetrieve(serverId, zoneId))
        return response.process()
    }

    /**
     * Send a DNS NOTIFY to all slaves.
     *
     * Fails when zone kind is not Master or Slave, or master and slave are disabled in the configuration. Only works for Slave if renotify is on. Clients MUST NOT send a body.
     *
     * Responses:
     * * 200 OK – OK
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server id
     * @param zoneId The zone id
     * @return [Result] object with [Unit]
     */
    suspend fun notify(serverId: String, zoneId: String): Result<Unit> = runCatching {
        val response = client.put(Zones.Id.Notify(serverId, zoneId))
        return response.process()
    }
    
    /**
     * Rectify the zone data.
     *
     * This does not take into account the API-RECTIFY metadata. Fails on slave zones and zones that do not have DNSSEC.
     *
     * Responses:
     * * 200 OK – OK Returns: string
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server id
     * @param zoneId The zone id
     * @return [Result] object with [String]
     */
    suspend fun rectify(serverId: String, zoneId: String): Result<String> = runCatching {
        val response = client.put(Zones.Id.Rectify(serverId, zoneId))
        return response.process()
    }
}