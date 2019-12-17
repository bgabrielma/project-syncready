const db = require('../config/db')

const loginOrRegister = function(req, res) {
  // res.render('index', { title: 'SyncReady', page: 'register' });

  res.redirect('/main?method=login')
}

const main = function(req, res) {
  const method = req.query.method || 'login'
  let page = ''

  if (method) {
    if (method === 'login') page = 'login'
    else if (method === 'register') page = 'register'
  }

  res.render('index', { title: 'SyncReady', page })
}

module.exports = {
  loginOrRegister
}