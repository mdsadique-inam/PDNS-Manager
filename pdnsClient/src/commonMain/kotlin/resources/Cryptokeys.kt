package resources

import io.ktor.resources.*

@Resource("cryptokeys")
class Cryptokeys(val parent: Zones.Id) {
    constructor(serverId: String, zoneId: String) : this(Zones.Id(serverId, zoneId))

    @Resource("{cryptokeyId}")
    class Id(val parent: Cryptokeys, val cryptokeyId: Int) {
        constructor(serverId: String, zoneId: String, cryptokeyId: Int) : this(Cryptokeys(serverId, zoneId), cryptokeyId)
    }

}