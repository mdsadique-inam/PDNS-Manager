package resources

import io.ktor.resources.*



@Resource("/servers")
class Servers {

    @Resource("{serverId}")
    class Id(val parent: Servers = Servers(), val serverId: String) {
        constructor(serverId: String) : this(Servers(), serverId)
    }
}