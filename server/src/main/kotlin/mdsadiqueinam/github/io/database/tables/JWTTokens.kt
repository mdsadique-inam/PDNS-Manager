package mdsadiqueinam.github.io.database.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object JwtTokens : IntIdTable("jwt_tokens") {
    val userId = reference("user_id", Users)
    val name = varchar("name", length = 255)
    val type = varchar("type", length = 255)
    val token = varchar("token", length = 64)
    val refreshToken = varchar("refresh_token", length = 255)
    val expiresAt = datetime("expires_at").nullable()
    val refreshTokenExpiresAt = datetime("refresh_token_expires_at")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
}

class JwtToken(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<JwtToken>(JwtTokens)

    var user by UserDao referencedOn JwtTokens.userId
    var userId by JwtTokens.userId.transform({ EntityID(it, Users) }, { it.value })
    var name by JwtTokens.name
    var type by JwtTokens.type
    var token by JwtTokens.token
    var refreshToken by JwtTokens.refreshToken
    var expiresAt by JwtTokens.expiresAt
    var refreshTokenExpiresAt by JwtTokens.refreshTokenExpiresAt
    var createdAt by JwtTokens.createdAt
}
