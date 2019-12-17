const db = require('../config/db')
const randomToken = require('random-token')

const userController = require('./userController')
const roomController = require('./roomController')
const ticketController = require('./ticketController')

const verifyUser = function (req) {
  return !!req.userLogged
}

const validateIfUserIsAdminPlus = function (req) {
  return req.userLogged.type !== 'Cliente' && req.userLogged.type !== 'Técnico'
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

  // Admin+ user is required to access this page/operation
  if(!validateIfUserIsAdminPlus(req)) return res.redirect('/dashboard')

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

  // Admin+ user is required to access this page/operation
  if(!validateIfUserIsAdminPlus(req)) return res.redirect('/dashboard')

  let Alltypes = {}

    await db('Type_Of_User')
      .whereNotIn('type', ['Cliente', 'Entidade'])
      .then(types => Alltypes = types )

  // define req.body.companyCode
  req.body.companyCode = req.userLogged.company.uuid_company

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
  let uuid = ''

  // save user
  await userController.saveUser(req)
  .then(async (responseOne) => {
    await userController.findUUIDByID(responseOne[0])
      .then(response2 => {
        uuid = response2[0].pk_uuid
      })
  })
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

  // Insert to company
  await userController.registerToCompany(uuid, req.body.companyCode)
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

  let companyUUID = req.userLogged.company.uuid_company
    
  await db('Users')
    .select('Users.*', 'Type_Of_User.type', 'Companies.name')
    .join('Type_Of_User', 'Users.Type_Of_User_uuid_type_of_users', '=', 'Type_Of_User.uuid_type_of_users')
    .join('Users_has_Companies', 'Users_has_Companies.Users_pk_uuid', '=', 'Users.pk_uuid')
    .join('Companies', 'Companies.uuid_company', '=', 'Users_has_Companies.Companies_uuid_company')
    .where('Companies.uuid_company', '=', companyUUID)
    .where('Type_Of_User.type', '!=', 'Entidade')
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
const newRoom = async function(req, res) {

  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')
  
  // Admin+ user is required to access this page/operation
  if(!validateIfUserIsAdminPlus(req)) return res.redirect('/dashboard')

  // get all datasheets
  let datasheets = []

  await db('Datasheet')
    .then(data => datasheets = data)

  // get all members
  let members = []

  await db('Users')
    .select('Users.*', 'Type_Of_User.type', 'Companies.name')
    .innerJoin('Type_Of_User', 'Users.Type_Of_User_uuid_type_of_users', '=', 'Type_Of_User.uuid_type_of_users')
    .leftJoin('Users_has_Companies', 'Users_has_Companies.Users_pk_uuid', '=', 'Users.pk_uuid')
    .leftJoin('Companies', 'Companies.uuid_company', '=', 'Users_has_Companies.Companies_uuid_company')
    .then(data => members = data)
    
  // get all members
  let types = []

  await db('Type_Of_User')
    .select('Type_Of_User.Type')
    .then(data => types = data)
    

  const data = {
    token: randomToken(20),
    datasheets,
    types,
    members,
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
const listRoom = async function(req, res) {

  let needReloadForStatus = false
  
  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

  await db('Rooms')
    .select('Rooms.*', 'Datasheet.*', 'Ticket_Options.ticket_option_designation')
    .innerJoin('Datasheet', 'Datasheet.uuid_datasheet', '=', 'Rooms.Datasheet_uuid_datasheet')
    .innerJoin('Tickets', 'Tickets.Rooms_uuid_room', '=', 'Rooms.uuid_room')
    .innerJoin('Ticket_Options', 'Ticket_Options.uuid_ticket_options', '=', 'Tickets.Ticket_Options_uuid_ticket_options')
    .innerJoin('Users_has_Rooms', 'Users_has_rooms.Rooms_uuid_room', '=', 'Rooms.uuid_room')
    .innerJoin('Users', 'Users_has_Rooms.Users_pk_uuid', '=', 'Users.pk_uuid')
    .innerJoin('Type_Of_User', 'Type_Of_User.uuid_type_of_users', '=', 'Users.Type_Of_User_uuid_type_of_users')
    .innerJoin('Users_has_Companies', 'Users_has_Companies.Users_pk_uuid', '=', 'Users.pk_uuid')
    .innerJoin('Companies', 'Companies.uuid_company', '=', 'Users_has_Companies.Companies_uuid_company')
    .where('Type_Of_User.type', '=', 'Entidade')
    .andWhere('Companies.uuid_company', '=', req.userLogged.company.uuid_company)
    .then(data => {

      data = data.map(elem => {

        db('Users_has_Rooms')
          .count('Users_has_Rooms.Rooms_uuid_room', { as: 'count' })
          .innerJoin('Users', 'Users.pk_uuid', '=', 'Users_has_Rooms.Users_pk_uuid')
          .innerJoin('Type_Of_User', 'Users.Type_Of_User_uuid_type_of_users', '=', 'Type_Of_User.uuid_type_of_users')
          .innerJoin('Rooms', 'Rooms.uuid_room', '=', 'Users_has_Rooms.Rooms_uuid_room')
          .innerJoin('Tickets', 'Tickets.Rooms_uuid_room', '=', 'Rooms.uuid_room')
          .innerJoin('Ticket_Options', 'Ticket_Options.uuid_ticket_options', '=', 'Tickets.Ticket_Options_uuid_ticket_options')
          .where('Users_has_Rooms.Rooms_uuid_room', `${elem.uuid_room}`)
          .andWhere('Ticket_Options.ticket_option_designation', '=', 'Técnico pendente')
          .andWhere('Type_Of_User.type', '=', 'Técnico')
          .then(async response => {
            if (response[0].count) {
              await ticketController.updateTicketByRoomAndTicketOptions(elem.uuid_room, '2')
            }
          })

        return elem
      })

      return res.render('index', 
      { 
        title:  'Lista de salas | SyncReady', 
        page: 'dashboard', 
        data, 
        userLogged: req.userLogged,
        subPage: 'rooms/list_room' 
      })
    })
}

const registerRoom = async function(req, res) {
  // const filename = !!req.file.filename ? req.file.filename : req.body.datasheet

  await roomController.saveRoom(req, res)
    .then(_ => { res.redirect('/dashboard/room/list') })
    .catch(err => {
      return res.send('error')
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

const newCompany = function(req, res) {
  // if user is not logged
  if(!verifyUser(req)) return res.redirect('/main')

  res.render('index', 
    { 
      title:  'Lista de empresas parceiras | SyncReady', 
      page: 'dashboard', 
      data: {},
      userLogged: req.userLogged,
      subPage: 'company/new_company_form' 
    })
}

const registerCompany = async function(req, res) {

  const { name, username, companyAddress, companyTelephone, companyCC, companyEmail, password } = req.body

  await db('Companies')
    .insert({
      name,
      company_address: companyAddress,
      company_telephone: companyTelephone,
      company_email: companyEmail
    })
    .then(async _ => {

      /* create user process for entity */
      let userTypeUUID = ''
      let userUUID = ''

      await userController.getUserType('Entidade').then(response => userTypeUUID = response[0].uuid_type_of_users)

      await db('Users')
        .insert({
          nickname: username,
          fullname: name,
          address: companyAddress,
          email: companyEmail,
          telephone: companyTelephone,
          citizencard: companyCC,
          password,
          Type_Of_User_uuid_type_of_users: userType
        })
        .then(response => {

          // get uuid for user
          userUUID = response[0].pk_uuid
        })

        /* create relation User_Has_Companies */
    })
}

module.exports = {
  dashboard,
  newUser,
  registerUser,
  listUsers,
  newRoom,
  listRoom,
  registerRoom,
  newAlert,
  listAlert,
  newCompany,
  registerCompany,
  logout,
}