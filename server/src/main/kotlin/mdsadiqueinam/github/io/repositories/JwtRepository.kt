package mdsadiqueinam.github.io.repositories

import mdsadiqueinam.github.io.database.services.JwtService
import models.User
import org.koin.core.annotation.Single

@Single
class JwtRepository(val jwtService: JwtService) : Repository() {
    suspend fun validate(token: String?): User? {
        TODO("Not yet implemented")
    }
}