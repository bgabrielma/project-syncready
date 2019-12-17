const db = require('../config/db')
const randomToken = require('random-token')

const dashboard = async function(req, res) {
  
  const pk_uuid = req.cookies['SYNCREADY_COOKIE_LOGINs']

  res.send(pk_uuid === null);

  return;
  await db('users')
    .where(pk_uuid)

  res.render('index', { title: 'Dashboard | SyncReady', page: 'dashboard', subPage: 'dashboard/home'} )
}

/* Users */
const newUser = function(req, res) {
  res.render('index', { title: 'New user | SyncReady', page: 'dashboard', subPage: 'user/new_user_form'} )
}

const listUsers = function(req, res) {
  res.render('index', { title: 'List of users | SyncReady', page: 'dashboard', subPage: 'user/list_user'} )
}

/* Rooms */
const newRoom = function(req, res) {
  const payload = {
    token: randomToken(20)
  }
  res.render('index', { title:  'New room | SyncReady', page: 'dashboard', subPage: 'rooms/new_room_form', payload })
}

const listRoom = function(req, res) {
  res.render('index', { title:  'List of rooms | SyncReady', page: 'dashboard', subPage: 'rooms/list_room' })
}

module.exports = {
  dashboard,
  newUser,
  listUsers,
  newRoom,
  listRoom
}