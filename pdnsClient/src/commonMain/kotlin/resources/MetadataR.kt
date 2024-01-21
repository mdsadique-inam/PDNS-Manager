package resources

import io.ktor.resources.*

@Resource("metadata")
class MetadataR(val parent: Zones.Id) {
    constructor(serverId: String, zoneId: String) : this(Zones.Id(serverId, zoneId))

    @Resource("{kind}")
    class Kind(val parent: MetadataR, val kind: String) {
        constructor(serverId: String, zoneId: String, kind: String) : this(MetadataR(serverId, zoneId), kind)
    }
}