const db = require('../config/db')

const saveRoom = function(req, filename, res) {

  const { nameRoom, description, roomCode, datasheet, members, untilAt } = req.body

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