package mdsadiqueinam.github.io.resources

import io.ktor.resources.Resource
import resources.Servers
import resources.Zones

@Resource("{action}")
class RRSets(val parent: Zones.Id, val action: RRSetsAction) {

    enum class RRSetsAction {
        ADD, UPDATE, DELETE
    }
}