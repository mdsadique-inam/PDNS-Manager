package mdsadiqueinam.github.io.models

import kotlinx.serialization.Serializable
import mdsadiqueinam.github.io.database.tables.UserDao
import mdsadiqueinam.github.io.serializers.SLocalDateTime
import mdsadiqueinam.github.io.serializers.SUUID

@Serializable
data class User(
    val id: SUUID? = null,
    val name: String,
    val username: String,
    val email: String,
    val createdAt: SLocalDateTime? = null,
    val updatedAt: SLocalDateTime? = null,
) : AbstractModel<SUUID>(id, UserDao) {

    companion object {
        fun fromDao(dao: UserDao): User {
            UserDao.table
            return User(
                id = dao.id.value,
                name = dao.name,
                username = dao.username,
                email = dao.email,
                createdAt = dao.createdAt,
                updatedAt = dao.updatedAt,
            )
        }
    }
}
