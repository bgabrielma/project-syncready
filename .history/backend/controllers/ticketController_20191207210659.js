const db = require('../config/db')

const getUUIDicketOptionsByID = function(id) {
  return db('Ticket_Options')
    .select('Ticket_Options.uuid_ticket_options')
    .where({
      id_ticket_options: id
    })
}

const newTicket = async function(req, id) {

    let ticketOptionUUID = ''

    await getUUIDicketOptionsByID(id)
    .then(async data => ticketOptionUUID = data[0].uuid_ticket_options)

    
    await db('Tickets')
    .insert({
      Users_pk_uuid: req.userLogged.pk_uuid,
      created_at: db.raw('NOW()'),
      updated_at: db.raw('NOW()'),
      Ticket_Options_uuid_ticket_options: ticketOptionUUID,
      Rooms_uuid_room: roomUUID
    })
    .catch(err => {
      return new Error('Error on inserting new file!')
    })
}

const post = async function(req, res) {

  const { statusTicketId } = req.body

  await newTicket(req, statusTicketId || '1')
    .then(_ => res.status(200).send({ ok: true }))
    .catch(err => res.status(500).send({ err, ok: false }))
}

module.exports = {
  newTicket,
  post
}