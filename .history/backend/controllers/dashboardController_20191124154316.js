const db = require('../config/db')

const dashboard = function(req, res) {
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
  res.render('index', { title:  'New room | SyncReady', page: 'dashboard', })
}

const listRoom = function(req, res) {
  res.render('index', { title:  'List of rooms | SyncReady', page: 'dashboard', })
}

module.exports = {
  dashboard,
  newUser,
  listUsers
}