const db = require('../config/db')
const randomToken = require('random-token')

const dashboard = async function(req, res) {
  
  const pk_uuid = req.cookies['SYNCREADY_COOKIE_LOGIN']

  if(!pk_uuid) {
    res.redirect('/main')
    return
  }

  await db('Users')
    .select('Users.*', 'Type_Of_User.*')
    .join('Type_Of_User', 'Users.Type_Of_User_uuid_type_of_users', '=', 'Type_Of_User.pk_type_of_user')
    .then(r => {
      res.render('index', { title: 'Dashboard | SyncReady', page: 'dashboard', data: r[0], subPage: 'dashboard/home'} )
    })
    .catch(err => res.send(err))
}

/* Users */
const newUser = function(req, res) {
  res.render('index', { title: 'New user | SyncReady', page: 'dashboard', data: {}, subPage: 'user/new_user_form'} )
}

/* Logout */
const logout = function(req, res) {
  res.clearCookie('SYNCREADY_COOKIE_LOGIN')
  res.redirect('/main')
}

const listUsers = function(req, res) {
  res.render('index', { title: 'List of users | SyncReady', page: 'dashboard', data: {}, subPage: 'user/list_user'} )
}

/* Rooms */
const newRoom = function(req, res) {
  const data = {
    token: randomToken(20)
  }
  res.render('index', { title:  'New room | SyncReady', page: 'dashboard', data:{}, subPage: 'rooms/new_room_form', payload })
}

const listRoom = function(req, res) {
  res.render('index', { title:  'List of rooms | SyncReady', page: 'dashboard', subPage: 'rooms/list_room' })
}

module.exports = {
  dashboard,
  newUser,
  listUsers,
  newRoom,
  listRoom,
  logout
}