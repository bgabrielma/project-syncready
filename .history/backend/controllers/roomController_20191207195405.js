const db = require('../config/db')

const saveRoom = async function(req, filename, res) {

  const { nameRoom, description, roomCode, datasheet, members, untilAt } = req.body

  let datasheetUUID = ''

  if(req.file) {

    // insert datasheet
    await db('Datasheet')
      .insert({
        description,
        created_at: db.raw('NOW()'),
        update_at: db.raw('NOW()'),
        designation: req.file.filename
      })
      .then(data => {

        // find datasheet UUID
        await db('Datasheet')
          .select('uuid_datasheet')
          .where({ pk_datasheet: data[0].pk_datasheet })
        
      })
      .catch(err => {
        return new Error('Error on inserting new file!')
      })

  }

  res.send({body: req.body, filename})

  /* 
  return db('Rooms')
    .insert({
      room_code: roomCode,
      name_room: nameRoom, 
      created_at: db.raw('NOW()'),
      update_at: db.raw('NOW()'),
      until_at: untilAt,
      Datasheet_uuid_datasheet: filename, 
      Status_Room_uuid_status_room: ''
    }) */
}

const post = function(req, res) {
  return res.send(req.body)
}

module.exports = {
  post,
  saveRoom
}