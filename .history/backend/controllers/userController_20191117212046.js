const db = require('../config/db')
const validadorCC = require('../utils/validadorCC')

const getUsers = function(req, res) {
  res.send('123')
}

const validateRegister = function(req, res) {
  
  const errors = {}

  const emptyField = { message: 'Este campo não pode estar vazio!' }

  const { fullname, email, contacto, cc, username, password, repeatpassword } = req.body

  if (!fullname) errors.fullname = emptyField 
  if (!email) erros.email = emptyField
  if (!cc || validadorCC(cc) !== 1)


}

module.exports = {
  getUsers,
  validateRegister
}