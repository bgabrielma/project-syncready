const db = require('./db')

const login = function(req, res, next) {
  res.render('index', { title: 'SyncReady' });
}

module.exports = {
  login
}