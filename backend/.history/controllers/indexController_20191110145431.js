const db = require('./db')

const login = function(req, res, next) {
  console.log(db)
  res.render('index', { title: 'SyncReady' });
}

module.exports = {
  login
}