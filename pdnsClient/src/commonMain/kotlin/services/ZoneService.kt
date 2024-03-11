package services

import extensions.process
import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import models.RRSet
import models.RRSetsBody
import models.Zone
import models.ZoneBody
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
     * @param serverId The server id. The id of the server to retrieve
     * @param zone The zone name.
     * When set to the name of a zone, only this zone is returned.
     * If no zone with that name exists, the response is an empty array.
     * This can be used to check if a zone exists in the database
     * without having to guess/encode the zone’s id or to check if a zone exists.
     * @param dnssec Whether to include DNSSEC information.
     * 'true' (default) or 'false', whether to include the “dnssec” and ”edited_serial” fields in the Zone objects.
     * Setting this to 'false' will make the query a lot faster.
     * @return [Result] object with [List] of [Zone]s
     */
    suspend fun fetchZones(serverId: String, zone: String? = null, dnssec: Boolean? = null) = fetchZones(Zones(serverId, zone, dnssec))

    /**
     * Fetches the zones from the server.
     *
     * @see [fetchZones] for more information.
     *
     * @param zones The zones object containing request information.
     * @return The result of the fetch operation, which contains a list of Zone objects if successful,
     *     or an exception if an error occurred.
     */
    suspend fun fetchZones(zones: Zones): Result<List<Zone>> =
        runCatching {
            val response = client.get(zones)
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
     * @param serverId The server id. The id of the server to retrieve
     * @param rrsets Whether to include RRsets.
     * @param body The zone to create
     * “true” (default) or “false”, whether to include the “rrsets” in the response Zone object.
     * @return [Result] object with [Zone]
     */
    suspend fun createZone(serverId: String, rrsets: Boolean? = null, body: ZoneBody) = createZone(Zones(serverId, null, null, rrsets), body)

    /**
     * Creates a new zone using the specified [zones] and [body].
     *
     * @see [createZone] for more information.
     *
     * @param zones The Zones object representing the zones endpoint.
     * @param body The ZoneBody object representing the body of the zone.
     * @return A Result object that contains the newly created Zone if successful, or the exception if failed.
     */
    suspend fun createZone(zones: Zones, body: ZoneBody): Result<Zone> = runCatching {
        val response = client.post(zones) {
            setBody(body)
        }
        return response.process()
    }

    /**
     * Get the zone managed by a server
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
    ) = fetchZone(Zones.Id(serverId, zoneId, rrsets, rrsetName, rrsetType))

    /**
     * Fetches the zone from the server.
     * @see [fetchZone] for more information.
     *
     * @param zonesId The zones id object containing request information.
     * @return The result of the fetch operation, which contains a Zone object if successful,
     *    or an exception if an error occurred.
     */
    suspend fun fetchZone(
        zonesId: Zones.Id
    ): Result<Zone> = runCatching {
        val response = client.get(zonesId)
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
    suspend fun axfrZone(serverId: String, zoneId: String) = axfrZone(Zones.Id.Export(serverId, zoneId))

    /**
     * Fetches the zone in AXFR format.
     * @see [axfrZone] for more information.
     *
     * @param zonesIdExport The zones id export an object containing request information.
     * @return The result of the fetch operation, which contains a string if successful,
     *   or an exception if an error occurred.
     */
    suspend fun axfrZone(zonesIdExport: Zones.Id.Export): Result<String> = runCatching {
        val response = client.get(zonesIdExport)
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
    suspend fun updateZone(serverId: String, zoneId: String, zone: ZoneBody) = updateZone(Zones.Id(serverId, zoneId), zone)

    /**
     * Updates the zone using the specified [zonesId] and [zone].
     * @see [updateZone] for more information.
     *
     * @param zonesId The zones id object containing request information.
     * @param zone The ZoneBody object representing the body of the zone.
     * @return The result of the update operation, which contains a Unit if successful,
     *  or an exception if an error occurred.
     */
    suspend fun updateZone(zonesId: Zones.Id, zone: ZoneBody): Result<Unit> = runCatching {
        val response = client.put(zonesId) {
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
     * @param zoneId The zone id
     * @param rrSets The list of RRsets to patch
     */
    suspend fun patchRRSets(serverId: String, zoneId: String, rrsets: List<RRSet>) = patchRRSets(Zones.Id(serverId, zoneId), rrsets)

    /**
     * Creates/modifies/deletes RRsets present in the payload and their comments. Returns 204 No Content on success.
     * @see [patchRRSets] for more information.
     *
     * @param zonesId The zones id object containing request information.
     * @param rrsets The list of RRsets to patch
     * @return The result of the patch operation, which contains a Unit if successful,
     * or an exception if an error occurred.
     */
    suspend fun patchRRSets(zonesId: Zones.Id, rrsets: List<RRSet>): Result<Unit> = runCatching {
        val body = RRSetsBody(rrsets)
        val response = client.patch(zonesId) {
            setBody(body)
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
    suspend fun deleteZone(serverId: String, zoneId: String) = deleteZone(Zones.Id(serverId, zoneId))

    /**
     * Deletes the zone using the specified [zonesId].
     * @see [deleteZone] for more information.
     *
     * @param zonesId The zones id object containing request information.
     * @return The result of the delete operation, which contains a Unit if successful,
     * or an exception if an error occurred.
     */
    suspend fun deleteZone(zonesId: Zones.Id): Result<Unit> = runCatching {
        val response = client.delete(zonesId)
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
    suspend fun retrieveSlaveFromMaster(serverId: String, zoneId: String) = retrieveSlaveFromMaster(Zones.Id.AxfrRetrieve(serverId, zoneId))

    /**
     * Fetches the slave zone from its master.
     * @see [retrieveSlaveFromMaster] for more information.
     *
     * @param zonesIdAxfrRetrieve The zones id axfr retrieve object containing request information.
     * @return The result of the fetch operation, which contains a Unit if successful,
     * or an exception if an error occurred.
     */
    suspend fun retrieveSlaveFromMaster(zonesIdAxfrRetrieve: Zones.Id.AxfrRetrieve): Result<Unit> = runCatching {
        val response = client.put(zonesIdAxfrRetrieve)
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
    suspend fun notify(serverId: String, zoneId: String) = notify(Zones.Id.Notify(serverId, zoneId))

    /**
     * Sends a DNS NOTIFY to all slaves.
     *
     * @see [notify] for more information.
     *
     * @param zonesIdNotify The zones id notify object containing request information.
     * @return The result of the fetch operation, which contains a Unit if successful,
     * or an exception if an error occurred.
     */
    suspend fun notify(zonesIdNotify: Zones.Id.Notify): Result<Unit> = runCatching {
        val response = client.put(zonesIdNotify)
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
    suspend fun rectify(serverId: String, zoneId: String) = rectify(Zones.Id.Rectify(serverId, zoneId))

    /**
     * Rectifies the zone data.
     * @see [rectify] for more information.
     *
     * @param zonesIdRectify The zones id rectify object containing request information.
     * @return The result of the fetch operation, which contains a string if successful,
     * or an exception if an error occurred.
     */
    suspend fun rectify(zonesIdRectify: Zones.Id.Rectify): Result<String> = runCatching {
        val response = client.put(zonesIdRectify)
        return response.process()
    }
}