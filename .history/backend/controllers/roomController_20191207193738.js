const db = require('../config/db')

const saveRoom = function(req, filename, res) {

  const { } = req.body

  return db('Rooms')
    .insert({
      room_code: req
    })
}

const post = function(req, res) {
  return res.send(req.body)
}

module.exports = {
  post,
  saveUser
}