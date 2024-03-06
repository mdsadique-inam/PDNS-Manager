package mdsadiqueinam.github.io.database.services

import mdsadiqueinam.github.io.database.tables.UserEntity
import mdsadiqueinam.github.io.database.tables.Users
import mdsadiqueinam.github.io.extensions.toUUID
import models.User
import org.jetbrains.exposed.sql.or
import org.koin.core.annotation.Single
import java.util.UUID

@Single
class UserService {
    fun find(id: UUID): UserEntity? =
        UserEntity
            .find { Users.id eq id }
            .firstOrNull()

    fun findByUsernameOrEmail(uid: String): UserEntity? =
        UserEntity
            .find { (Users.username eq uid) or (Users.email eq uid) }
            .firstOrNull()

    fun create(user: User, password: String): UserEntity {
        return UserEntity.new {
            this.username = user.username
            this.email = user.email
            this.name = user.name
            this.password = password
        }
    }

    fun update(user: User, password: String?): UserEntity {
        val userEntity = find(user.id.toUUID()) ?: throw NoSuchElementException("User not found")
        userEntity.apply {
            this.username = user.username
            this.email = user.email
            this.name = user.name
            this.updatedAt = user.updatedAt
            password?.let { this.password = it }
        }
        return userEntity
    }
}
