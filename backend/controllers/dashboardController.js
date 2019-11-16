const db = require('../config/db')

const dashboard = function(req, res) {
  res.render('index', { title: 'SyncReady', page: 'dashboard'} )
}

module.exports = {
  dashboard
}