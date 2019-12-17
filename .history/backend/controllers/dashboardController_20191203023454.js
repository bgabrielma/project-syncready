const db = require('../config/db')
const randomToken = require('random-token')

const userController = require('./userController')

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
    .whereNotIn('type', ['Cliente', 'Entidade'])
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
      .whereNotIn('type', ['Cliente', 'Entidade'])
      .then(types => Alltypes = types )

  // define req.body.companyCode

  await userController.findCompanyByUUID(req.userLogged.pk_uuid)
  .then(r => req.body.companyCode = r[0].uuid_company)

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


  // getting uuid
  let uuid = '123'

  // save user
  await userController.saveUser(req)
  .then(async (responseOne) => {

    res.send(uuid)
   /*  await userController.findUUIDByID(responseOne[0])
      .then(response2 => {
        uuid = response2[0].pk_uuid
        console.log(uuid)
      })
      .catch(err => res.send(err)) */
  })
  .catch(err => {
    res.send('Error')
    /*errors.errorOnInsert = userController.messageErrorOnInsert
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
    }) */
  })

  return
  
  // Insert to company
  await registerToCompany(uuid, req.body.companyCode)
    .then(_ => res.redirect('/dashboard/user/list'))
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
/* Logout */
const logout = function(req, res) {
  res.clearCookie('SYNCREADY_COOKIE_LOGIN')
  res.redirect('/main')
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

/* Rooms */
const newRoom = function(req, res) {

  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

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
  newUser,
  registerUser,
  listUsers,
  newRoom,
  listRoom,
  newAlert,
  listAlert,
  newTicket,
  listTicket,
  logout,
}