package mdsadiqueinam.github.io.repositories

import io.ktor.http.*
import mdsadiqueinam.github.io.database.services.UserService
import mdsadiqueinam.github.io.database.tables.UserEntity
import mdsadiqueinam.github.io.exceptions.HttpException
import models.User
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Single
import java.util.*

@Single
class UserRepository(private val userService: UserService) {
    private fun entityToModel(userEntity: UserEntity): User {
        return User(
            id = userEntity.id.toString(),
            username = userEntity.username,
            email = userEntity.email,
            name = userEntity.name,
            createdAt = userEntity.createdAt,
            updatedAt = userEntity.updatedAt
        )
    }

    fun findOrNull(id: String): User? {
        val userEntity = userService.find(UUID.fromString(id))
        return userEntity?.let { entityToModel(it) }
    }

    fun find(id: String): User {
        return findOrNull(id) ?: throw NoSuchElementException("User not found")
    }

    fun login(uid: String, password: String) {
        transaction {
            val userEntity = userService.findByUsernameOrEmail(uid)
            if (userEntity == null || userEntity.password != password) {
                throw HttpException(HttpStatusCode.Unauthorized, "Invalid username or password")
            }
        }
    }
}