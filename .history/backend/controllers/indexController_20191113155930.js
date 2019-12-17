const db = require('../config/db')

const loginOrRegister = function(req, res) {
  // res.render('index', { title: 'SyncReady', page: 'register' });

  res.redirect('/main?m=l')
}

const main = function(req, res) {
  const method = req.query.m || 'login'
  let page = ''

  console.log(method)
    
  if (method != 'r') page = 'login'
  else page = 'register'

  // res.render('index', { title: 'SyncReady', page } )

  res.status(500)
  res.render('error', { error: new Error('és gay') })
}

module.exports = {
  loginOrRegister,
  main
}