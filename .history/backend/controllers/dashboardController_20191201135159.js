const db = require('../config/db')
const randomToken = require('random-token')

const userController = require('./userController')


/* Whenever dashboard controller is in use, execute this functions */
let userLogged = {}

function verifyUser() {
  return !!req.userLogged
}

const dashboard = async function(req, res) {
  if(verifyUser)
  res.render('index', { title: 'Dashboard | SyncReady', page: 'dashboard', userLogged, subPage: 'dashboard/home'} )
}

/* Users */
const newUser = async function(req, res) {
  await db('Type_Of_User')
    .then(types => {
      res.render('index', 
      { 
        title: 'Registar novo utilizador | SyncReady', 
        page: 'dashboard', 
        subPage: 'user/new_user_form',
        userLogged,
        types,
        errors: {}, 
        predata: {},
      })
    })
}

const registerUser = async function(req, res) {
  const { errors } = await userController.validateNewUser(req)

  if(Object.keys(errors).length > 0) {
    return res.render('index', 
    { 
      title: 'Registar novo utilizador | SyncReady', 
      page: 'dashboard', data: {}, 
      subPage: 'user/new_user_form',
      errors, 
      predata: req.body,
    })
  }

  return res.render('index', 
    { 
      title: 'Utilizador registado | SyncReady', 
      page: 'dashboard', data: {}, 
      subPage: 'success'
    })
}
/* Logout */
const logout = function(req, res) {
  res.clearCookie('SYNCREADY_COOKIE_LOGIN')
  res.redirect('/main')
}

const listUsers = function(req, res) {
  res.render('index', { title: 'Lista de utilizadores | SyncReady', page: 'dashboard', data: {}, subPage: 'user/list_user'} )
}

/* Rooms */
const newRoom = function(req, res) {
  const data = {
    token: randomToken(20)
  }
  res.render('index', { title:  'Registar nova sala | SyncReady', page: 'dashboard', data, subPage: 'rooms/new_room_form' })
}

const listRoom = function(req, res) {
  res.render('index', { title:  'Lista de salas | SyncReady', page: 'dashboard', data: {}, subPage: 'rooms/list_room' })
}

const newTicket = function(req, res) {
  res.render('index', { title:  'Registar novo ticket | SyncReady', page: 'dashboard', data: {}, subPage: 'tickets/new_ticket_form' })
}

const listTicket = function(req, res) {
  res.render('index', { title:  'Lista de tickets | SyncReady', page: 'dashboard', data: {}, subPage: 'tickets/list_ticket' })
}

module.exports = {
  dashboard,
  newUser,
  registerUser,
  listUsers,
  newRoom,
  listRoom,
  newTicket,
  listTicket,
  logout,
}