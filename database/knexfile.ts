import type { Knex } from "knex";

const migrations: Knex.MigratorConfig & Record<string, any> = {
  tableName: "knex_migrations",
  schemaName: 'public',
  extension: '.ts',
  loadExtensions: ['.ts'],
  getNewMigrationName: (name: string) => {
    return `${Date.now()}_${name}.ts`;
  },
};

const config: Knex.Config = {
  client: "pg",
  connection: {
    database: "powerdns",
    user: "struxe",
    password: "strongPassword",
  },
  pool: {
    min: 2,
    max: 10,
  },
  migrations,
};

export default config;
