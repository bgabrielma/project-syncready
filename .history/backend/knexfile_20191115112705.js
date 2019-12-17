// Update with your config settings.

module.exports = {
  client: 'mysql2',
  connection: {
    host: process.env.APP_DB_HOST || 'localhost',
    database: process.env.APP_DB_DATABASE || 'syncready_database',
    user:     process.env.MYSQL_USER || 'root',
    password: process.env.MYSQL_ROOT_PASSWORD || ''
  },
  pool: {
    min: 2,
    max: 10
  },
  migrations: {
    tableName: 'syncready_database_migrations'
  }
};
