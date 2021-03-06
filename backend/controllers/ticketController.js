const db = require('../config/db')

const getUUIDTicketOptionsByID = function(id) {
  
  return db('Ticket_Options')
    .select('Ticket_Options.uuid_ticket_options')
    .where({
      id_ticket_options: id
    })
}

const updateTicketByRoomAndTicketOptions = async function(uuid_room, ticketId) {

  let ticketOptionUUID = {}

  await getUUIDTicketOptionsByID(`${ticketId}`)
    .then(data => ticketOptionUUID = data[0].uuid_ticket_options)

  console.log(ticketOptionUUID)

  await db('Tickets')
    .update({
      Ticket_Options_uuid_ticket_options: ticketOptionUUID
    })
    .where('Tickets.Rooms_uuid_room', '=', `${uuid_room}`)
}

const newTicket = async function(req, id, roomUUID) {

  let ticketOptionUUID = ''

  await getUUIDTicketOptionsByID(id)
  .then(async data => ticketOptionUUID = data[0].uuid_ticket_options)
  
  return db('Tickets')
    .insert({
      Users_pk_uuid: req.userLogged.pk_uuid,
      created_at: db.raw('NOW()'),
      updated_at: db.raw('NOW()'),
      Ticket_Options_uuid_ticket_options: ticketOptionUUID,
      Rooms_uuid_room: roomUUID
    })
}

const post = async function(req, res) {

  const { statusTicketId } = req.body

  await newTicket(req, statusTicketId || '1')
    .then(_ => res.status(200).send({ ok: true }))
    .catch(err => res.status(500).send({ err, ok: false }))
}

module.exports = {
  updateTicketByRoomAndTicketOptions,
  newTicket,
  post
}