// Update with your config settings.

module.exports = {
  client: 'mysql2',
  host: "0.0.0.0",
  connection: {
    database: 'syncready_database',
    user:     'root',
    password: ''
  },
  pool: {
    min: 2,
    max: 10
  },
  migrations: {
    tableName: 'syncready_database_migrations'
  }
};
