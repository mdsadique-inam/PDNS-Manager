package mdsadiqueinam.github.io.repositories

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import mdsadiqueinam.github.io.database.services.UserService
import models.User
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Single
import java.util.UUID
import kotlin.NoSuchElementException

@Single
class UserRepository(private val userService: UserService) {

    fun findOrNull(id: String): User? {
        return transaction {
            val userEntity = userService.find(UUID.fromString(id))
            userEntity?.toModel()
        }
    }

    fun find(id: String): User {
        return findOrNull(id) ?: throw NoSuchElementException("User not found")
    }

    fun update(updateUser: User) {
        transaction {
            userService.update(
                updateUser.copy(
                    updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                ), null
            )
        }
    }

}