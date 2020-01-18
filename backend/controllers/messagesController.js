const db = require('../config/db')

const get = async (req, res) => {

  const { content, userUUID, roomUUID } = req.query

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
        Status_Message_uuid_status_message: res[0].uuid_status_message
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
      sentAt: dateNow
    }
}

module.exports = {
  get,
  newMessage
}
