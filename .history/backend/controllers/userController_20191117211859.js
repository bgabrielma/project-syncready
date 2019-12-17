const db = require('../config/db')

const getUsers = function(req, res) {
  res.send('123')
}

const validateRegister = function(req, res) {
  
  const errors = {}

  const emptyField = { message: 'Este campo não pode estar vazio!' }

  const { fullname, email, contacto, cc, username, password, repeatpassword } = req.body

  if(!fullname) {
    errors.fullname = 
  }

}

module.exports = {
  getUsers,
  validateRegister
}