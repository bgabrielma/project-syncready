const db = require('../config/db')

const loginOrRegister = function(req, res) {
  res.render('index', { title: 'SyncReady' });
}

module.exports = {
  login
}