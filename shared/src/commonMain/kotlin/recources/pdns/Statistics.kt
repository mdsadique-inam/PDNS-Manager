package recources.pdns

import io.ktor.resources.*

/**
 * Query statistics.
 *
 * Query PowerDNS internal statistics.
 *
 * Responses:
 * * 200 OK – List of [models.pdns.StatisticItem] Returns: array
 * * 400 Bad Request – The supplied request was not valid Returns: [models.pdns.Error] object
 * * 404 Not Found – Requested item was not found Returns: [models.pdns.Error] object
 * * 422 Unprocessable Entity – Returned when a non-existing statistic name has been requested. Contains an error message
 * * 500 Internal Server Error – Internal server error Returns: [models.pdns.Error] object
 *
 * @param parent The [Servers.Id] instance.
 * @param statistic The name of the statistic to retrieve.
 * When set to the name of a specific statistic, only this value is returned.
 * If no statistic with that name exists, the response has a 422 status and an error message.
 * @param includerings “true” (default) or “false”,
 * whether to include the Ring items, which can contain thousands of log messages or queried domains.
 * Setting this to ”false” may make the response a lot smaller.
 */
@Resource("statistics")
class Statistics(val parent: Servers.Id, val statistic: String? = null, val includerings: Boolean? = null) {
}