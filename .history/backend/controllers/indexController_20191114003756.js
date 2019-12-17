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

  res.render('index', { title: 'SyncReady', page } )
}

const checkdb = function(req, res) {
  let message = '' 
  db.raw('select 1+1 as result').catch(err => {
   message = 'database with error'
  }).then(() => message = 'database ok')

  res.send({message})
}


module.exports = {
  loginOrRegister,
  main,
  checkdb
}