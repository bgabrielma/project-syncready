const config = require('../knexfile')
const knex = require('knex')(config)

// load automatically all "sql commands"
knex.migrate.latest([config])

module.exports = knex