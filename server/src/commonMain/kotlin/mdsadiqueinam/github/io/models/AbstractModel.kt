package mdsadiqueinam.github.io.models

import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.jetbrains.exposed.dao.DaoEntityID
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

@Serializable
abstract class AbstractModel<ID : Comparable<ID>>(
    @Transient val daoId: ID? = null,
    @Transient val entityClass: EntityClass<ID, Entity<ID>>? = null
) {

    init {
        require(entityClass != null) { "entityClass must be set" }
    }

    val entityID: EntityID<ID>
        get() = DaoEntityID(daoId!!, entityClass!!.table)

    suspend fun toDao(): Entity<ID>? {
        return newSuspendedTransaction(Dispatchers.IO) {
            daoId?.let { entityClass!!.findById(entityID) }
        }
    }
}
