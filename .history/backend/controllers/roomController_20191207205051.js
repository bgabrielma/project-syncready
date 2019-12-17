const db = require('../config/db')

const saveRoom = async function(req, res) {

  const { nameRoom, description, roomCode, datasheet, members, untilAt } = req.body

  let datasheetUUID = ''

  if(req.file) {

    // create new datasheet

    await db('Datasheet')
      .insert({
        description,
        created_at: db.raw('NOW()'),
        updated_at: db.raw('NOW()'),
        designation: req.file.filename
      })
      .then(async data => {

        // find datasheet UUID
        await db('Datasheet')
          .select('uuid_datasheet')
          .where({ 
            pk_datasheet: data
          })
          .then(data => {
            datasheetUUID = data[0].uuid_datasheet
          })
      })
      .catch(err => {
        return new Error('Error on inserting new file!')
      })
  } else datasheetUUID = datasheet

  // create new room

  // store uuid's room for ticket operation
  let roomUUID = ''

  await db('Rooms')
    .insert({
      room_code: roomCode,
      name_room: nameRoom, 
      created_at: db.raw('NOW()'),
      updated_at: db.raw('NOW()'),
      until_at: untilAt,
      Datasheet_uuid_datasheet: datasheetUUID,
    })
    .then(async data => {

      // get new's uuid room
      await db('Rooms')
        .select('Rooms.uuid_room')
        .where({
          pk_rooms: data
        })
        .then(async data => {
          roomUUID = data[0].uuid_room

          // put users in room
          
          members.forEach(async elem => {
            await db('Users_has_Rooms')
              .insert({
                Users_pk_uuid: elem,
                Rooms_uuid_room: roomUUID
              })
          })
        })
    })

    // after this whole process xD - create new ticket - final process

    // get ticket "Técnico em falta"'s option uuid

  await db('Ticket_Options')
    .select('Ticket_Options.uuid_ticket_options')
    .where({
      id_ticket_options: '1'
    })
    .then(async data => {
      await db('Tickets')
        .insert({
          Users_pk_uuid: req.userLogged.pk_uuid,
          created_at: db.raw('NOW()'),
          updated_at: db.raw('NOW()'),
          Ticket_Options_uuid_ticket_options: data[0].uuid_ticket_options,
          Rooms_uuid_room: roomUUID
        })
    })


  return res.send({ ok: true })
}

const post = function(req, res) {
  return res.send(req.body)
}

module.exports = {
  post,
  saveRoom
}