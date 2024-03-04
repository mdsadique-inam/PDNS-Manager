package mdsadiqueinam.github.io.database.tables

import models.User
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

object Users : UUIDTable() {
    val name = varchar("name", length = 250)
    val username = varchar("username", length = 250).uniqueIndex()
    val email = varchar("email", length = 250).uniqueIndex()
    val password = varchar("password", length = 250)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(Users)

    var name by Users.name
    var username by Users.username
    var email by Users.email
    var password by Users.password
    var createdAt by Users.createdAt
    var updatedAt by Users.updatedAt

    fun toModel() = User(
        id = id.toString(),
        username = username,
        email = email,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
