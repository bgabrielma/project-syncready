const configSocket = (io) => {

  io.on('connection', function (socket) {

    socket.on("joinRoomList", function(pk_uuid, room_uuid) {
      socket.pk_uuid = pk_uuid
      socket.room_uuid = room_uuid

      // connect socket to room
      socket.join(room_uuid)
    })

    socket.on("messageHasSent", function() {
      io.to(socket.room_uuid).emit("newMessages")
    })
  })
}

module.exports = {
  configSocket
}
