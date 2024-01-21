package resources

import io.ktor.resources.*

@Resource("tsigkeys")
class TSIGKeys(val parent: Servers.Id) {
    constructor(serverId: String) : this(Servers.Id(serverId))

    @Resource("{tsigKeyId}")
    class Id(val parent: TSIGKeys, val tsigKeyId: String) {
        constructor(serverId: String, tsigKeyId: String) : this(TSIGKeys(serverId), tsigKeyId)
    }
}