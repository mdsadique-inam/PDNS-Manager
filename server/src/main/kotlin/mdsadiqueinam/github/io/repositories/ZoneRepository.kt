package mdsadiqueinam.github.io.repositories

import org.koin.core.annotation.Single
import services.ZoneService

@Single
class ZoneRepository(private val zoneService: ZoneService) {

}