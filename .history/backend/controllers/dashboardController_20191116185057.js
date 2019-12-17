const db = require('../config/db')

const dashboard = function(req, res) {
  res.render('user/list_user', { title: 'SyncReady', page: 'dashboard'} )
}

module.exports = {
  dashboard
}