const db = require('../config/db')

const dashboard = function(req, res) {
  res.render('index', { title: 'SyncReady', page: 'dashboard', subPage: '/dashboard/home'} )
}

const newUser = function(req, res) {
  res.render('index', { title: 'SyncReady', page: 'dashboard', subPage: 'new_user_form'} )
}

const listUsers = function(req, res) {
  res.render('index', { title: 'SyncReady', page: 'dashboard', subPage: 'list_user'} )
}

module.exports = {
  dashboard,
  newUser,
  listUsers
}