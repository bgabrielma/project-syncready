// Update with your config settings.

module.exports = {
  client: 'mysql2',
  connection: {
    host: process.env.APP_DB_HOST,
    database: process.env.APP_DB,
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
