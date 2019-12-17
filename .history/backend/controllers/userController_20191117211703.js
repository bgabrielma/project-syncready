const db = require('../config/db')

const getUsers = function(req, res) {
  res.send('123')
}

const validateRegister = function(req, res) {
  
  const erros = []

  const { fullname, email, contacto, cc, username, password, repeatpassword } = req.body

  if(!fullname) {
    res.send('123')
  }

}

module.exports = {
  getUsers,
  validateRegister
}