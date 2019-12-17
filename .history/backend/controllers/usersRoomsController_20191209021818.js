const db = require('../config/db')

const get = async function(req, res) {
  const instance = db('Users_has_Rooms')

  const { roomUUID, userUUID } = req.query

  if (roomUUID && !userUUID)
    instance.where('Users_has_Rooms.Rooms_uuid_room', '=', `${roomUUID}`)
  else if (!roomUUID && userUUID)
    instance.where('Users_has_Rooms.Users.pk_uuid', '=', `${userUUID}`)
  else instance.where('Users_has_Rooms.Rooms_uuid_room', '=', `${roomUUID}`).andWhere('Users_has_Rooms.Users.pk_uuid', '=', `${userUUID}`)
    

  instance
    .then(data => res.status(200).send(data))
    .catch(err => res.status(500).send(err))
}

module.exports = {

}