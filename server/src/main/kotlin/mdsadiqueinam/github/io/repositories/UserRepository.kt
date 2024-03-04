package mdsadiqueinam.github.io.repositories

import mdsadiqueinam.github.io.database.services.UserService
import models.User
import org.koin.core.annotation.Single
import java.util.*

@Single
class UserRepository(private val userService: UserService) {

    fun findOrNull(id: String): User? {
        val userEntity = userService.find(UUID.fromString(id))
        return userEntity?.toModel()
    }

    fun find(id: String): User {
        return findOrNull(id) ?: throw NoSuchElementException("User not found")
    }

}