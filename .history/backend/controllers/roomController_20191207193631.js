const db = require('../config/db')

const saveRoom = function(req, filename, res) {
  return db('Rooms')
}

const post = function(req, res) {
  return res.send(req.body)
}

module.exports = {
  post,
  saveUser
}