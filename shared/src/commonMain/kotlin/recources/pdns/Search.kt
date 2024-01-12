package recources.pdns

import io.ktor.resources.*
import kotlinx.serialization.SerialName

/**
 * Search the data inside PowerDNS
 *
 * Search the data inside PowerDNS for search_term and return at most max_results.
 * This includes zones, records and comments.
 * The * character can be used in search_term as a wildcard character and the ? character can be used as a wildcard for a single character.
 *
 * Responses:
 * * 200 OK – Returns a JSON array with results Returns: array of [models.pdns.SearchResult] objects
 * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
 * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
 * * 422 Unprocessable Entity – The input to the operation was not valid Returns: [models.pdns.Error] object
 * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
 *
 * @param parent The [Servers.Id] instance.
 * @param q The search term.
 * @param max The maximum number of results to return.
 * @param objectType The type of object to search for, from “all”, “zone”, “record”, “comment”.
 */
@Resource("search-data")
class Search(val parent: Servers.Id, val q: String, val max: Int, @SerialName("object_type") val objectType: String){
}