package mdsadiqueinam.github.io.database.services

import mdsadiqueinam.github.io.database.tables.JwtTokenEntity
import mdsadiqueinam.github.io.database.tables.JwtTokens
import org.koin.core.annotation.Single

@Single
class JwtService {
    fun findByToken(token: String): JwtTokenEntity? =
        JwtTokenEntity.find { JwtTokens.token eq token }.firstOrNull()
}
