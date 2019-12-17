const db = require('../config/db')

const loginOrRegister = function(req, res) {
  // res.render('index', { title: 'SyncReady', page: 'register' });

  res.redirect('/main?m=l')
}

const main = function(req, res) {
  const method = req.query.method || 'l'
  let page = ''
    
  if (method !== 'r') page = 'l'
  else page = 'r'

  res.render('index', { title: 'SyncReady', page })
}

module.exports = {
  loginOrRegister,
  main
}