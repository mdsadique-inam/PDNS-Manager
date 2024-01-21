package resources

import io.ktor.resources.*


@Resource("autoprimaries")
class AutoPrimaries(val parent: Servers.Id) {
    constructor(serverId: String) : this(Servers.Id(serverId))

    @Resource("{ip}/{nameserver}")
    class Delete(val parent: AutoPrimaries, val ip: String, val nameserver: String) {
        constructor(serverId: String, ip: String, nameserver: String) : this(AutoPrimaries(serverId), ip, nameserver)
    }
}