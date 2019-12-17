// Update with your config settings.

module.exports = {
  client: 'mysql2',
  connection: {
    host: process.env.APP_DB_HOST,
    database: process.env.APP_DB_DATABASE,
    user:     process.env.MYSQL_USER,
    password: process.env.MYSQL_PASSWORD
  },
  pool: {
    min: 2,
    max: 10
  },
  migrations: {
    tableName: 'syncready_database_migrations'
  }
};
