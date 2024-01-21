package services

import extensions.process
import io.ktor.client.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import models.Cryptokey
import models.CryptokeyBody
import resources.Cryptokeys

class CryptokeyService(private val client: HttpClient) {


    /**
     * Get all CryptoKeys for a zone, except the privateKey
     *
     * Responses:
     * * 200 OK – List of Cryptokey objects Returns: array of [models.Cryptokey] objects
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The ID of the server.
     * @param zoneId The ID of the zone.
     * @return A [Result] with the [List] of [Cryptokey]s.
     */
    suspend fun fetchCryptokeys(serverId: String, zoneId: String): Result<List<Cryptokey>> = runCatching {
        val response = client.get(Cryptokeys(serverId, zoneId))
        return response.process()
    }

    /**
     * Returns all data about the CryptoKey, including the privatekey.
     *
     * Responses:
     * * 200 OK – Cryptokey Returns: [models.Cryptokey] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The ID of the server.
     * @param zoneId The ID of the zone.
     * @param cryptokeyId The ID of the cryptokey.
     * @return A [Result] with the [Cryptokey] object.
     */
    suspend fun fetchCryptokey(serverId: String, zoneId: String, cryptokeyId: Int): Result<Cryptokey> = runCatching {
        val response = client.get(Cryptokeys.Id(serverId, zoneId, cryptokeyId))
        return response.process()
    }

    /**
     * Creates a Cryptokey
     *
     * This method adds a new key to a zone. The key can either be generated or imported by supplying the content parameter.
     * if content, bits and algo are null,
     * a key will be generated based on the default-ksk-algorithm and default-ksk-size settings for a KSK and
     * the default-zsk-algorithm and default-zsk-size options for a ZSK.
     *
     * Responses:
     * * 201 Created – Created Returns: [models.Cryptokey] object
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The ID of the server.
     * @param zoneId The ID of the zone.
     * @param body The [CryptokeyBody] instance.
     * @return A [Result] with the [Cryptokey] object.
     */
    suspend fun createCryptokey(serverId: String, zoneId: String, body: CryptokeyBody): Result<Cryptokey> =
        runCatching {
            val response = client.post(Cryptokeys(serverId, zoneId)) {
                setBody(body)
            }
            return response.process()
        }

    /**
     * This method (de)activates a key from zone_name specified by cryptokey_id.
     *
     * Responses:
     * * 204 No Content – OK
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The ID of the server.
     * @param zoneId The ID of the zone.
     * @param cryptokeyId The ID of the cryptokey.
     * @param active Whether the cryptokey is active.
     * @return A [Result] with the [Unit] object.
     */
    suspend fun updateCryptokey(serverId: String, zoneId: String, cryptokeyId: Int, active: Boolean): Result<Unit> =
        runCatching {
            val body = CryptokeyBody(active = active)
            val response = client.put(Cryptokeys.Id(serverId, zoneId, cryptokeyId)) {
                setBody(body)
            }
            return response.process()
        }

    /**
     * Deletes a cryptokey from a specific zone in a server.
     *
     * Responses:
     * * 204 No Content – OK
     * * 400 Bad Request – The supplied request was not valid Returns: [models.Error] object
     * * 404 Not Found – Requested item was not found Returns: [models.Error] object
     * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.Error] object
     * * 500 Internal Server Error – Internal server error Returns: [models.Error] object
     *
     * @param serverId The ID of the server.
     * @param zoneId The ID of the zone.
     * @param cryptokeyId The ID of the cryptokey to delete.
     * @return A [Result] with the [Unit] object.
     */
    suspend fun deleteCryptokey(serverId: String, zoneId: String, cryptokeyId: Int): Result<Unit> = runCatching {
        val response = client.delete(Cryptokeys.Id(serverId, zoneId, cryptokeyId))
        return response.process()
    }
}