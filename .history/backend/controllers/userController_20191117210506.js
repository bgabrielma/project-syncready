const db = require('../config/db')

const getUsers = function(req, res) {
  res.send('123')
}

const validateRegister = function(req, res) {
  res.send(req.body)
}

module.exports = {
  getUsers,
  validateRegister
}