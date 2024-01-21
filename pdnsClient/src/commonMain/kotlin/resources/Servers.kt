package resources

import io.ktor.resources.*
import kotlinx.serialization.SerialName
import models.SearchType
import models.StatisticType

@Resource("/servers")
class Servers {
    @Resource("{serverId}")
    class Id(val parent: Servers = Servers(), val serverId: String) {
        constructor(serverId: String) : this(Servers(), serverId)
    }

    @Resource("search-data")
    class Search(val parent: Id, val q: String, val max: Int, @SerialName("object_type") val objectType: SearchType){
        constructor(serverId: String, q: String, max: Int, objectType: SearchType) : this(Id(serverId), q, max, objectType)
    }

    @Resource("statistics")
    class Statistics(val parent: Id, val statistic: StatisticType? = null, val includerings: Boolean? = null) {
        constructor(serverId: String, statistic: StatisticType? = null, includerings: Boolean? = null) : this(Id(serverId), statistic, includerings)
    }
}