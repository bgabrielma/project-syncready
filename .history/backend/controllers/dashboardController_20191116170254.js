const db = require('../config/db')

const dashboard = function(req, res) {

  const page = 'dashboard'
  res.render('index', { title: 'SyncReady', page } )
}

module.exports = {
  dashboard
}