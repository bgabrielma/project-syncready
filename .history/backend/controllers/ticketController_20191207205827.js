const db = require('../config/db')

const getUUIDicketOptionsByID = function(req, id) {
  return db('Ticket_Options')
    .select('Ticket_Options.uuid_ticket_options')
    .where({
      id_ticket_options: '1'
    })
}