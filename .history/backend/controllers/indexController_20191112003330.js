const db = require('../config/db')

const login = function(req, res) {
  res.render('index', { title: 'SyncReady' });
}

module.exports = {
  login
}