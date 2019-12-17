const db = require('../config/db')
const validadorCC = require('../utils/validadorCC')

const getUsers = function(req, res) {
  res.send('123')
}

const validateRegister = function(req, res) {
  
  let errors = {}

  const emptyField = { message: 'Este campo não pode estar vazio!' }
  const differentPasswords = { message: 'As palavras-passes indicadas não correspondem uma com a outra!' }

  const { fullname, email, contacto, cc, username, password, repeatpassword } = req.body

  if (!fullname) errors.fullname = emptyField 
  if (!email) errors.email = emptyField
  if (!cc || validadorCC(cc) != 1) errors.cc = { message: 'O número de cartão de cidadão é inválido!' }
  if (!contacto || contacto.length != 9) errors.contacto = { message: 'O número de telefone é inválido!' }

  if (username) {
    // fazer sistema para verificar se tal username já existe
  } else errors.username = emptyField

  if (!password && !repeatpassword) errors.password = emptyField
  if (password != repeatpassword) errors.repeatpassword = differentPasswords

  
  if (errors) {
    res.render('index', { title: 'SyncReady', page: 'main/register', errors } )
  } else {

    // fetch user data
    
    const payload = {}

    res.render('dashboard', { title: 'SyncReady', page: 'main/register', errors } )
  }

  
}

module.exports = {
  getUsers,
  validateRegister
}