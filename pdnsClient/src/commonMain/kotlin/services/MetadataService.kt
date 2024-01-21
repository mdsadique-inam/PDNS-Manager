package services

import extensions.process
import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import models.Metadata
import resources.MetadataR

class MetadataService(val client: HttpClient) {

    /**
     * Get all the Metadata associated with the zone.
     *
     * Responses:
     * * 200 OK – List of Metadata objects Returns: array of [models.Metadata] objects
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server ID.
     * @param zoneId The zone ID.
     * @return A list of [Metadata] objects.
     */
    suspend fun fetchMetadataList(serverId: String, zoneId: String): Result<List<Metadata>> = runCatching {
        val response = client.get(MetadataR(serverId, zoneId))
        return response.process()
    }

    /**
     * Get the content of a single kind of domain metadata as a Metadata object.
     *
     * Responses:
     * * 200 OK – Metadata object with list of values Returns: [models.Metadata] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server ID.
     * @param zoneId The zone ID.
     * @param kind The kind of metadata to retrieve.
     * @return A [Metadata] object.
     */
    suspend fun fetchMetadata(serverId: String, zoneId: String, kind: String): Result<Metadata> = runCatching {
        val response = client.get(MetadataR.Kind(serverId, zoneId, kind))
        return response.process()
    }

    /**
     * Creates a set of metadata entries
     *
     * Creates a set of metadata entries of given kind for the zone. Existing metadata entries for the zone with the same kind are not overwritten.
     *
     * Responses:
     * * 204 No Content – OK
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server ID.
     * @param zoneId The zone ID.
     * @param metadata The [Metadata] object.
     * @return A [Unit] object.
     */
    suspend fun createMetadata(serverId: String, zoneId: String, metadata: Metadata): Result<Unit> = runCatching {
        val response = client.post(MetadataR(serverId, zoneId)) {
            setBody(metadata)
        }
        return response.process()
    }

    /**
     * Replace the content of a single kind of domain metadata.
     *
     * Creates a set of metadata entries of given kind for the zone. Existing metadata entries for the zone with the same kind are removed.
     *
     * Responses:
     * * 200 OK – Metadata object with list of values Returns: [models.Metadata] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server ID.
     * @param zoneId The zone ID.
     * @param kind The kind of metadata to retrieve.
     * @param metadata The [Metadata] object.
     */
    suspend fun updateMetadata(serverId: String, zoneId: String, kind: String, metadata: Metadata): Result<Unit> =
        runCatching {
            val response = client.put(MetadataR.Kind(serverId, zoneId, kind)) {
                setBody(metadata)
            }
            return response.process()
        }

    /**
     * Delete all items of a single kind of domain metadata.
     *
     * Responses:
     * * 204 No Content – OK
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The server ID.
     * @param zoneId The zone ID.
     * @param kind The kind of metadata to retrieve.
     */
    suspend fun deleteMetadata(serverId: String, zoneId: String, kind: String): Result<Unit> = runCatching {
        val response = client.delete(MetadataR.Kind(serverId, zoneId, kind))
        return response.process()
    }
}