const db = require('../config/db')

const post = async (req, res) => {

  const { content, userUUID, roomUUID } = req.body

  if (!content || !userUUID || !roomUUID) return res.status(401).send({ err: "Data missing" })  

  const call = await newMessage({ content, pk_uuid: userUUID, room_uuid: roomUUID })
    
  call.callback()
    .then(data => res.status(200).send({ ok: true, response: data }))
    .catch(err => res.status(500).send(err))
}

const newMessage = async (data) => {
      
  let newMessageUUID = ''
  console.log(data)

  const dateNow = new Date().toISOString().slice(0, 19).replace('T', ' ')

  await db('Status_Message')
    .where('Status_Message.pk_status_message', '1')
    .then(async res => {

      await db('Messages')
      .insert({
        content: data.content,
        created_at: dateNow,
        update_at: dateNow,
        Status_Message_uuid_status_message: res[0].uuid_status_message,
        isImage: data.isImage
      })
      .then(async response => {
        await db('Messages')
          .where('Messages.pk_message', response)
          .then(_response => {
            newMessageUUID = _response[0].uuid_message 
          })
      })
    })
    
    // insert in relation Messages_has_Users´

    return {
      callback: function() {
        return db('Messages_has_Users')
          .insert({
            Messages_uuid_message: newMessageUUID,
            Users_pk_uuid: data.pk_uuid,
            Rooms_uuid_room: data.room_uuid,
            sent_at: dateNow
          })
      },
      newMessageUUID
    }
}

const get = async (req, res) => {
  
  if (!req.query.roomUUID) {
    return res.status(401).send({ err: "Messages not found with room provider" })
  }
  
  await getMessage(req.query.roomUUID, null)
    .then(data => res.status(200).send({ ok: true, data }))
    .catch(err => res.status(500).send(err))

}

const getMessage = (roomUUID, messageUUID, isAsc = true) => {
  
  const query = db('Messages')
    .select('Users.fullname', 'Users.pk_uuid', 'Messages.*', 'Type_Of_User.type', 'Messages_has_Users.sent_at')
    .innerJoin('Messages_has_Users', 'Messages_has_Users.Messages_uuid_message', '=', 'Messages.uuid_message')
    .innerJoin('Users', 'Users.pk_uuid', '=', 'Messages_has_Users.Users_pk_uuid')
    .innerJoin('Type_Of_User', 'Users.Type_Of_User_uuid_type_of_users', '=', 'Type_Of_User.uuid_type_of_users')
    .where('Messages_has_Users.Rooms_uuid_room', roomUUID)
    .orderBy('Messages_has_Users.sent_at', isAsc ? 'ASC' : 'DESC')

    if(messageUUID)
      query.andWhere('Messages.uuid_message', '=', messageUUID)

  return query
}

const getLastMessage = async (req, res) => {

  if (!req.query.roomUUID) {
    return res.status(401).send({ err: "Messages not found with room provider" })
  }

  getMessage(req.query.roomUUID, null, false)
    .limit(1)
    .then(data => res.status(200).send({ ok: true, data }))
    .catch(err => res.status(500).send(err))
}

module.exports = {
  post,
  get,
  newMessage,
  getMessage,
  getLastMessage
}
