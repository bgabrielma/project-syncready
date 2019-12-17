const db = require('../config/db')
const validadorCC = require('../utils/validadorCC')

const registerNew = function() {
  
}

const login = function(email, password, res) {
  // get uuid
  const uuid = 'aindapordefinir'

  res.redirect(`/dashboard?uuid=${uuid}`)
}

const register = async function(req, res) {
  
  let errors = {}

  const emptyField = { message: 'Este campo não pode estar vazio!' }
  const invalidPass = { message: 'As palavras-passes indicadas não correspondem uma com a outra!' }

  const { fullname, address, email, contacto, cc, username, password, repeatpassword } = req.body

  if (!fullname) errors.fullname = emptyField
  if (!address) errors.address = emptyField 
  if (!email) errors.email = emptyField
  if (!cc || validadorCC(cc) !== 1) errors.cc = { message: 'O número de cartão de cidadão é inválido!' }
  if (!contacto || contacto.length !== 9) errors.contacto = { message: 'O número de telefone é inválido!' }

  if (username) {
    await db.count('nickname', { as: 'count' }).from('users')
      .where('nickname', username)
      .then(r => { 
        console.log(res[0].count)
      });
    return;
  } else errors.username = emptyField

  if (!password) errors.password = emptyField
  if (!repeatpassword) errors.repeatpassword = emptyField
  
  if (password != repeatpassword) {
    errors.repeatpassword = invalidPass
    errors.password = invalidPass
  } 

  if (Object.keys(errors).length > 0) {
    res.render('index', { title: 'SyncReady', page: 'main/register', errors , predata: req.body } )
  } else {
    
    // TODO: fazer sistema de registar

    // then login
    login(email, password, res)
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
    login(email, password, res);
  }
}

module.exports = {
  register,
  auth
}