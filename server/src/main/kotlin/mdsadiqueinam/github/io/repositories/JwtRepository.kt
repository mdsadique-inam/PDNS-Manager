package mdsadiqueinam.github.io.repositories

import mdsadiqueinam.github.io.models.User
import org.koin.core.annotation.Single

@Single
class JwtRepository : Repository() {
    suspend fun validate(token: String): User? {
        TODO("Not yet implemented")
    }
}