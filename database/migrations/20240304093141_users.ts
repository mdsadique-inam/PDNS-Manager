import type { Knex } from "knex";

const tableName = 'users'

export async function up(knex: Knex): Promise<any> {
    return knex.schema.createTable(tableName, (table) => {
        table.uuid('id').primary()
        table.string('name').notNullable()
        table.string('username').notNullable().unique()
        table.string('email').notNullable().unique()
        table.string('password').notNullable()
        table.timestamp("created_at", { useTz: true }).defaultTo(knex.fn.now())
        table.timestamp("updated_at", { useTz: true });
        table.check('email = lower(email)', [], 'lowercase_email')
    })
}


export async function down(knex: Knex): Promise<any> {
   return knex.schema.dropTable(tableName)
}

