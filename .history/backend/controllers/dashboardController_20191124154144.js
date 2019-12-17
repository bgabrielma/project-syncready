const db = require('../config/db')

const dashboard = function(req, res) {
  res.render('index', { title: 'SyncReady', page: 'dashboard', subPage: 'dashboard/home'} )
}

/* Users */
const newUser = function(req, res) {
  res.render('index', { title: 'SyncReady - new user', page: 'dashboard', subPage: 'user/new_user_form'} )
}

const listUsers = function(req, res) {
  res.render('index', { title: 'SyncReady - list of all users', page: 'dashboard', subPage: 'user/list_user'} )
}

/* Rooms */
const newRoom = function(req, res) {
  res.render('index', { title:  'SyncReady', page: 'dashboard', })
}

module.exports = {
  dashboard,
  newUser,
  listUsers
}