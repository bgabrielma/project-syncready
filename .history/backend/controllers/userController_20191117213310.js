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
  if (!email) errors.push({ field: 'email', message: emptyField })
  if (!cc || validadorCC(cc) != 1) errors.push({ field: 'cc', message: 'O número de cartão de cidadão é inválido!' })
  if (!contacto || contacto.length != 9) errors.push({ field: 'contacto', message: 'O número indicado está inválido!' })

  if (username) {
    // fazer sistema para verificar se tal username já existe
  } else errors.username = emptyField

  if (!password && !repeatpassword) errors = emptyField
  if (password != repeatpassword) errors = differentPasswords

  res.send(errors)

  
}

module.exports = {
  getUsers,
  validateRegister
}