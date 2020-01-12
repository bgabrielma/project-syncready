const configSocket = (io) => {

  io.on('connection', function (socket) {

    socket.on("joinRoomList", function(pk_uuid, room_uuid) {
      socket.pk_uuid = pk_uuid
      socket.room_uuid = room_uuid

      console.log(`New request from user with pk_uuid ${pk_uuid} and room_uuid ${ room_uuid }`)

      // connect socket to room
      socket.join(room_uuid)

      // trigger event
      /* io.to(room_uuid).emit("newMessages", {
        user: "Jorge Penedo da Rocha Calhau",
        message: "Eu sou uma pessoa"
      }) */
      
    })
  })
}

module.exports = {
  configSocket
}
