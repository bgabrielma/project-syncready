const db = require('../config/db')

const loginOrRegister = function(req, res) {
  // res.render('index', { title: 'SyncReady', page: 'register' });

  res.redirect('/main?method=login')
}

const main = function(req, res) {
  const method = req.query.method

  if (method) {

  }
}

module.exports = {
  loginOrRegister
}