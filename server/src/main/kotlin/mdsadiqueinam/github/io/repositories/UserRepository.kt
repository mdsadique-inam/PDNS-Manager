package mdsadiqueinam.github.io.repositories

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import mdsadiqueinam.github.io.database.services.UserService
import mdsadiqueinam.github.io.extensions.toUUID
import models.User
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.core.annotation.Single

@Single
class UserRepository(private val userService: UserService) {

    suspend fun findOrNull(id: String): User? {
        return newSuspendedTransaction {
            val userEntity = userService.find(id.toUUID())
            userEntity?.toModel()
        }
    }

    suspend fun find(id: String): User {
        return findOrNull(id) ?: throw NoSuchElementException("User not found")
    }

    suspend fun update(user: User): User {
        return newSuspendedTransaction {
            userService.update(
                user.copy(
                    updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                ), null
            ).toModel()
        }
    }

    suspend fun findByUsernameOrEmail(usernameOrEmail: String): User? {
        return newSuspendedTransaction {
            val userEntity = userService.findByUsernameOrEmail(usernameOrEmail)
            userEntity?.toModel()
        }
    }
}