module.exports = {
  client: 'mysql2',
  connection: {
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'SYNCREADY_DATABASE',
    multipleStatements: true
  },
  pool: {
    min: 2,
    max: 10
  },
  migrations: {
    tableName: 'syncready_app_migrations'
  }
}