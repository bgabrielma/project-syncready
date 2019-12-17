const db = require('../config/db')
const randomToken = require('random-token')

const userController = require('./userController')

const dashboard = async function(req, res) {
  
  const pk_uuid = req.cookies['SYNCREADY_COOKIE_LOGIN']

  console.log(req.cookies['SYNCREADY_COOKIE_LOGIN'])

  if(!pk_uuid) {
    res.redirect('/main')
    return
  }

  await db('Users')
    .select('Users.*', 'Type_Of_User.*')
    .join('Type_Of_User', 'Users.Type_Of_User_uuid_type_of_users', '=', 'Type_Of_User.pk_type_of_user')
    .where({
      pk_uuid
    })
    .then(r => {
      res.render('index', { title: 'Dashboard | SyncReady', page: 'dashboard', data: r[0], subPage: 'dashboard/home'} )
    })
    .catch(err => res.send(err))
}

/* Users */
const newUser = function(req, res) {
  res.render('index', 
    { 
      title: 'Registar novo utilizador | SyncReady', 
      page: 'dashboard', data: {}, 
      subPage: 'user/new_user_form',
      errors: {}, 
      predata: {},
    })
}

const registerUser = async function(req, res) {

  const { errors } = await userController.validateNewUser(req)

  if(Object.keys(errors).length > 0) {
    res.render('index', 
    { 
      title: 'Registar novo utilizador | SyncReady', 
      page: 'dashboard', data: {}, 
      subPage: 'user/new_user_form',
      errors, 
      predata: req.body,
    })
  }
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