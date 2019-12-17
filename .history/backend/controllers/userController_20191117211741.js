const db = require('../config/db')

const getUsers = function(req, res) {
  res.send('123')
}

const validateRegister = function(req, res) {
  
  const errors = {}

  const { fullname, email, contacto, cc, username, password, repeatpassword } = req.body

  if(!fullname) {
    errors.push()
  }

}

module.exports = {
  getUsers,
  validateRegister
}