const db = require('../config/db')
const validadorCC = require('../utils/validadorCC')

const messageErrorOnInsert = { message: 'Ocorreu um erro ao realizar o seu pedido - tente mais tarde :(' }

const emptyField = { message: 'Este campo não pode estar vazio!' }
const invalidPass = { message: 'As palavras-passes indicadas não correspondem uma com a outra!' }

const login = async function(email, password, res) {

  console.log(email, password, res);

  // get uuid
  const uuid = 'aindapordefinir'

  db('users').count('*', { as: 'count' })
    .where({
      email,
      password
    })
    .then(r => {
      if(r[0].count) {
        // TODO: mecanismo do token ou cookie por definir

        res.redirect(`/dashboard?uuid=${uuid}`)
      } else
        messageErrorOnInsert.message = 'Email e/ou password inválido(s)!'
        res.render('index', { title: 'SyncReady', page: 'main/login', errors: { }, messageErrorOnInsert } )
    })
}

const register = async function(req, res) {
  let errors = {}
  const { fullname, address, email, contacto, cc, username, password, repeatpassword } = req.body

  if (!fullname) errors.fullname = emptyField
  if (!address) errors.address = emptyField 
  if (email) {
    await db('users').count('email', { as: 'count' })
      .where('email', email)
      .then(r => { 
        if (r[0].count) {
          errors.email = { message: 'O email indicado já pertence a outra conta!'}
        }
      });
  } else errors.email = emptyField
  if (!cc || validadorCC(cc) !== 1) errors.cc = { message: 'O número de cartão de cidadão é inválido!' }
  if (!contacto || contacto.length !== 9) errors.contacto = { message: 'O número de telefone é inválido!' }

  if (username) {
    await db('users').count('nickname', { as: 'count' })
      .where('nickname', username)
      .then(r => { 
        if (r[0].count) {
          errors.username = { message: 'O nome de utilizador indicado já está em uso!'}
        }
      });
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
    await db('users').insert({
      nickname: username,
      fullname,
      address, 
      email,
      telephone: contacto, 
      citizencard: cc,
      password
    })
    .catch(_ => {
      errors.errorOnInsert = messageErrorOnInsert
      res.render('index', { title: 'SyncReady', page: 'main/register', errors , predata: { } } )

      // just to confirm
      return;
    })
    // In order to avoid the following bug: First, the system needs to execute the main query -> insert and, after this task is completed, execute the login query
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
    res.render('index', { title: 'SyncReady', page: 'main/login', errors, messageErrorOnInsert: null })
  } else {
    login(email, password, res);
  }
}

module.exports = {
  register,
  auth
}