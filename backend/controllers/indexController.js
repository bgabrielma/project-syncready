const db = require('../config/db')

const loginOrRegister = function(req, res) {
  // res.render('index', { title: 'SyncReady', page: 'register' });

  res.redirect('/main?m=l')
}

const main = function(req, res) {

  if(!!req.userLogged) return res.redirect('/dashboard')

  const method = req.query.m || 'login'
  let page = ''
    
  if (method != 'r') page = 'login'
  else page = 'register'

  res.render('index', { title: 'SyncReady', page: `main/${page}`, errors: {}, predata: {}, messageErrorOnInsert: null } )
}

const checkdb = async function(req, res) {

  let message = {
    status: 'ok'
  }
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