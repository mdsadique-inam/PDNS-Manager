package mdsadiqueinam.github.io.repositories

import mdsadiqueinam.github.io.database.services.UserService
import models.User
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Single
import java.util.*

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

}