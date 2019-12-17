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

          // put users in room
          
          members.forEach(async elem => {
            await db('Users_has_Rooms')
              .insert({
                Users_pk_uuid: elem,
                Rooms_uuid_room: data[0].uuid_room
              })
          })
        })
    })

    // after this whole process xD - create new ticket - final process

    // get ticket "Técnico em falta"'s option uuid


    return res.send({ ok: true })
}

const post = function(req, res) {
  return res.send(req.body)
}

module.exports = {
  post,
  saveRoom
}