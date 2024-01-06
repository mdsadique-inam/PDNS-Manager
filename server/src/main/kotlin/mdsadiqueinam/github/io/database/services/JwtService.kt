package mdsadiqueinam.github.io.database.services

import mdsadiqueinam.github.io.database.tables.JwtToken
import mdsadiqueinam.github.io.database.tables.JwtTokens
import org.koin.core.annotation.Single

@Single
class JwtService {
    fun findByToken(token: String): JwtToken? =
        JwtToken.find { JwtTokens.token eq token }.firstOrNull()
}
