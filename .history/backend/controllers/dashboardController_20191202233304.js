const db = require('../config/db')

const userController = require('./userController')
const roomController = require('./roomController')

const { randomToken } = roomController

function verifyUser(req) {
  return !!req.userLogged
}

const dashboard = async function(req, res) {

  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

  return res.render('index', 
    { 
      title: 'Dashboard | SyncReady', 
      page: 'dashboard', 
      userLogged: req.userLogged, 
      subPage: 'dashboard/home'
    })
}

/* Users */
const newUser = async function(req, res) {

  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

  await db('Type_Of_User')
    .then(types => {
      res.render('index', 
      { 
        title: 'Registar novo utilizador | SyncReady', 
        page: 'dashboard', 
        subPage: 'user/new_user_form',
        userLogged: req.userLogged,
        types,
        errors: {}, 
        predata: {},
      })
    })
}

const registerUser = async function(req, res) {

  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

  let Alltypes = {}

  await db('Type_Of_User')
    .then(types => Alltypes = types )

  const { errors } = await userController.validateNewUser(req)

  if(Object.keys(errors).length > 0) {
    return res.render('index', 
    { 
      title: 'Registar novo utilizador | SyncReady', 
      page: 'dashboard', 
      data: {}, 
      subPage: 'user/new_user_form',
      userLogged: req.userLogged,
      types: Alltypes,
      errors, 
      predata: req.body,
    })
  }

  const { fullname, address, email, contacto, cc, username, password, type } = req.body

  await db('Users')
    .insert({
    nickname: username,
    fullname,
    address, 
    email,
    telephone: contacto,
    citizencard: cc,
    password,
    Type_Of_User_uuid_type_of_users: type
  })
  .then(() => res.redirect('/dashboard/user/list'))
  .catch(_ => {
    errors.errorOnInsert = userController.messageErrorOnInsert
    return res.render('index', 
    { 
      title: 'Registar novo utilizador | SyncReady', 
      page: 'dashboard', 
      data: {}, 
      subPage: 'user/new_user_form',
      userLogged: req.userLogged,
      types: Alltypes,
      errors, 
      predata: {},
    })
  })
}

const listUsers = async function(req, res) {

  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

  await db('Users')
    .then(data => {
      return res.render('index', 
      { 
        title: 'Lista de utilizadores | SyncReady', 
        page: 'dashboard', 
        data, 
        userLogged: req.userLogged,
        subPage: 'user/list_user'
      })
    })
}

/* Logout */
const logout = function(req, res) {
  res.clearCookie('SYNCREADY_COOKIE_LOGIN')
  res.redirect('/main')
}

/* Rooms */
const newRoom = async function(req, res) {

  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

  
  let listDataSheets, members = []

  // get all list of dataSheets

  /* await db('Datasheet')
    .then(response => listDataSheets = response) */

  await db('Users')
    .select('Users.*', 'Type_Of_User.*')
    .join('Type_Of_User', 'Users.Type_Of_User_uuid_type_of_users', '=', 'Type_Of_User.uuid_type_of_users')
    .whereIn('Type_Of_User.type', 'Técnico, Cliente')
    .then(response => res.send(response))
    .catch(err => res.send(err))

  return

  const data = {
    token: randomToken(20)
  }

  return res.render('index', 
    { 
      title:  'Registar nova sala | SyncReady', 
      page: 'dashboard', 
      data, 
      userLogged: req.userLogged,
      subPage: 'rooms/new_room_form' 
    })
}

const registerRoom = function(req, res) {
  res.send(req.body)
}

const listRoom = function(req, res) {

  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

  return res.render('index', 
    { title:  'Lista de salas | SyncReady', 
      page: 'dashboard', 
      data: {}, 
      userLogged: req.userLogged,
      subPage: 'rooms/list_room' 
    })
}

/* Ticket */
const newTicket = function(req, res) {

  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

  return res.render('index', 
    { 
      title:  'Registar novo ticket | SyncReady', 
      page: 'dashboard', 
      data: {},
      userLogged: req.userLogged, 
      subPage: 'tickets/new_ticket_form' 
    })
}

const listTicket = function(req, res) {

  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

  return res.render('index', 
    { 
      title:  'Lista de tickets | SyncReady', 
      page: 'dashboard', 
      data: {},
      userLogged: req.userLogged, 
      subPage: 'tickets/list_ticket' 
    })
}

/* Alert */
const newAlert = function(req, res) {

  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

  res.render('index', 
    { 
      title:  'Registar novo alerta | SyncReady', 
      page: 'dashboard', 
      data: {},
      userLogged: req.userLogged,
      subPage: 'alerts/new_alert_form' 
    })
}

const listAlert = function(req, res) {

  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

  res.render('index', 
    { 
      title:  'Lista de alertas | SyncReady', 
      page: 'dashboard', 
      data: {},
      userLogged: req.userLogged,
      subPage: 'alerts/list_alert' 
    })
}

module.exports = {
  dashboard,
  verifyUser,
  newUser,
  registerUser,
  listUsers,
  newRoom,
  registerRoom,
  listRoom,
  newAlert,
  listAlert,
  newTicket,
  listTicket,
  logout,
}