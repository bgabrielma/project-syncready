const db = require('../config/db')

const get = async function(req, res) {

  if(!req.query.roomUUID) {
    return res.status(401).send({ err: 'Users not found in room provider' })
  }

  await db('Users')
    .select('Users.*', 'Type_Of_User.type')
    .innerJoin('Type_Of_User', 'Users.Type_Of_User_uuid_type_of_users', '=', 'Type_Of_User.uuid_type_of_users')
    .innerJoin('Users_has_Rooms', 'Users.pk_uuid', '=', 'Users_has_Rooms.Users_pk_uuid')
    .innerJoin('Rooms', 'Users_has_Rooms.Rooms_uuid_room', '=', 'Rooms.uuid_room')
    .then(data => res.status(200).send({ ok: true, data }))
    .catch(err => res.status(500).send(err))
    
}

module.exports = {
  get
}