const db = require('../config/db')

const dashboard = function(req, res) {
  res.render('user', { title: 'SyncReady', page: 'dashboard'} )
}

module.exports = {
  dashboard
}