const db = require('../config/db')
const randomToken = require('random-token')

const dashboard = async function(req, res) {
  
  const data = req.cookies['SYNCREADY_COOKIE_LOGIN']

  if(!pk_uuid) {
    res.redirect('/main')
    return
  }

  await db('users')
    .where('pk_uuid', pk_uuid)
    .then(r => {
      res.render('index', { title: 'Dashboard | SyncReady', page: 'dashboard', subPage: 'dashboard/home'} )
    })
    .catch(_ => res.send('An error occurred!'))
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