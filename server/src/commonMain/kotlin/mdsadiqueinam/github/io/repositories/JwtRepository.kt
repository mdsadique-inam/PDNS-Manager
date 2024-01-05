package mdsadiqueinam.github.io.repositories

import mdsadiqueinam.github.io.models.User
import org.koin.core.annotation.Single

@Single
expect class JwtRepository {
    suspend fun validate(token: String): User?
}