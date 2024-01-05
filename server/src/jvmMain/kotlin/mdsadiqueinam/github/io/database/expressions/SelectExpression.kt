package mdsadiqueinam.github.io.database.expressions

import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.QueryBuilder

class SelectExpression(private val functionCall: String, private val alias: String) : Expression<String>() {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) = queryBuilder {
        append("$functionCall AS $alias")
    }
}
