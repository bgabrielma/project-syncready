const db = require('../config/db')
const validadorCC = require('../utils/validadorCC')

const login = function(email, password) {
  // get uuid
  const uuid = 'aindapordefinir'

  res.redirect(`/dashboard?uuid=${uuid}`)
}

const register = function(req, res) {
  
  let errors = {}

  const emptyField = { message: 'Este campo não pode estar vazio!' }
  const differentPasswords = { message: 'As palavras-passes indicadas não correspondem uma com a outra!' }

  const { fullname, email, contacto, cc, username, password, repeatpassword } = req.body

  if (!fullname) errors.fullname = emptyField 
  if (!email) errors.email = emptyField
  if (!cc || validadorCC(cc) !== 1) errors.cc = { message: 'O número de cartão de cidadão é inválido!' }
  if (!contacto || contacto.length !== 9) errors.contacto = { message: 'O número de telefone é inválido!' }

  if (username) {
    // TODO: fazer sistema para verificar se tal username já existe
  } else errors.username = emptyField

  if (!password) errors.password = emptyField
  if (!repeatpassword) errors.repeatpassword = emptyField
  
  if (password != repeatpassword) errors.repeatpassword = differentPasswords

  if (Object.keys(errors).length > 0) {
    res.render('index', { title: 'SyncReady', page: 'main/register', errors , predata: req.body } )
  } else {
    login();
  }
}

const auth = function(req, res) {
  let errors = {}

  const emptyField = { message: 'Este campo não pode estar vazio!' }

  const { email, password } = req.body

  if (!email) errors.email = emptyField
  if (!password) errors.password = emptyField

   if (Object.keys(errors).length > 0) {
    res.render('index', { title: 'SyncReady', page: 'main/login', errors} )
  } else {
    login();
  }
}

module.exports = {
  register,
  auth
}