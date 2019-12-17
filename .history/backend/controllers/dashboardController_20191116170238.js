const db = require('../config/db')

const dashboard = function(req, res) {

  res.render('dashboard', { title: 'SyncReady' } )
}

module.exports = {
  dashboard
}