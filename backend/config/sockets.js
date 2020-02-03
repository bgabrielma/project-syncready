const messagesController = require('../controllers/messagesController')

const configSocket = (io) => {

  io.on('connection', function (socket) {

    socket.on("joinRoomList", function(pk_uuid, room_uuid) {
      socket.pk_uuid = pk_uuid
      socket.room_uuid = room_uuid

      console.log(`New request from user with pk_uuid ${pk_uuid} and room_uuid ${ room_uuid }`)

      // connect socket to room
      socket.join(room_uuid)
    })

    socket.on("newMessage", async function(pk_uuid, room_uuid, message, isImage = 0) {
      const call = await messagesController.newMessage({ content: message, pk_uuid, room_uuid, isImage })

      await call.callback()
          .then(async _ => {
            
            await messagesController.getMessage(room_uuid, call.newMessageUUID)
              .then(newMessage => {
                newMessage[0].sent_at = new Date(newMessage[0].sent_at).toISOString().slice(0, 19).replace('T', ' ')

                if (isImage) newMessage[0].isImage = 1

                io.to(room_uuid).emit("refreshGroupMessages", { newMessage })
              })
          })
    })
  })
}

module.exports = {
  configSocket
}
