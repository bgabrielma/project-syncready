const db = require('../config/db')
const validadorCC = require('../utils/validadorCC')

const getUsers = function(req, res) {
  res.send('123')
}

const validateRegister = function(req, res) {
  
  let errors = []

  const emptyField = { message: 'Este campo não pode estar vazio!' }
  const differentPasswords = { message: 'As palavras-passes indicadas não correspondem uma com a outra!' }

  const { fullname, email, contacto, cc, username, password, repeatpassword } = req.body

  if (!fullname) errors.push({ field: 'fullname', message: emptyField })

  res.send(errors)

  
}

module.exports = {
  getUsers,
  validateRegister
}