package mdsadiqueinam.github.io.repositories

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import mdsadiqueinam.github.io.database.services.UserService
import mdsadiqueinam.github.io.extensions.toUUID
import models.User
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Single

@Single
class UserRepository(private val userService: UserService) {

    fun findOrNull(id: String): User? {
        return transaction {
            val userEntity = userService.find(id.toUUID())
            userEntity?.toModel()
        }
    }

    fun find(id: String): User {
        return findOrNull(id) ?: throw NoSuchElementException("User not found")
    }

    fun update(user: User): User {
        return transaction {
            userService.update(
                user.copy(
                    updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                ), null
            ).toModel()
        }
    }

}