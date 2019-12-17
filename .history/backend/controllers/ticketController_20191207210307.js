const db = require('../config/db')

const getUUIDicketOptionsByID = function(req, res, id) {
  return db('Ticket_Options')
    .select('Ticket_Options.uuid_ticket_options')
    .where({
      id_ticket_options: id
    })
}

const newTicket = async function(req, res, id) {
  return getUUIDicketOptionsByID(id)
    .then(async data => {
      await db('Tickets')
        .insert({
          Users_pk_uuid: req.userLogged.pk_uuid,
          created_at: db.raw('NOW()'),
          updated_at: db.raw('NOW()'),
          Ticket_Options_uuid_ticket_options: data[0].uuid_ticket_options,
          Rooms_uuid_room: roomUUID
        })
        .catch(err => {
          return new Error('Error on inserting new file!')
        })
    })
}

const post = function(req, res) {
}