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

const checkdb = async function(req, res) {
  let message = 'test' 
  await db.raw('select 1+1 as result').catch(err => {
    message = err
  });

  res.send(message)
}


module.exports = {
  loginOrRegister,
  main,
  checkdb
}