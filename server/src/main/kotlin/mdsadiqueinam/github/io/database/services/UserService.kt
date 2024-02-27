package mdsadiqueinam.github.io.database.services

import mdsadiqueinam.github.io.database.tables.UserEntity
import mdsadiqueinam.github.io.database.tables.Users
import org.jetbrains.exposed.sql.or
import org.koin.core.annotation.Single
import java.util.*

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
}
