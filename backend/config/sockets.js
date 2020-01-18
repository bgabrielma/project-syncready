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

    socket.on("newMessage", async function(pk_uuid, room_uuid, message) {
      const call = await messagesController.newMessage({ content: message, pk_uuid, room_uuid })

      await call.callback()
          .then(_ => {
            io.to(room_uuid).emit("refreshGroupMessages", {
              content: message,
              pk_uuid,
              room_uuid,
              sentAt: call.sentAt
            })
          })
    })
  })
}

module.exports = {
  configSocket
}
