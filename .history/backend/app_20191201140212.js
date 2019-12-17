const createError = require('http-errors')

const express = require('express')
const routes = require('./config/routes')

const path = require('path')
const cookieParser = require('cookie-parser')
const logger = require('morgan')

const helmet = require('helmet')

const db = require('./config/db')

const app = express()

// view engine setup
app.set('views', path.join(__dirname, 'views'))
app.set('view engine', 'ejs')

// disable cache and activate security
app.use(helmet())

app.use(logger('dev'))
app.use(express.json())
app.use(express.urlencoded({ extended: true }))
app.use(cookieParser())
app.use(express.static(path.join(__dirname, 'public')))

// store user logged if cookie exists
app.use(async function (req, res, next) {
  const pk_uuid = req.cookies['SYNCREADY_COOKIE_LOGIN']

  req.userLogged = null

  if(pk_uuid) {
    await db('Users')
      .select('Users.*', 'Type_Of_User.*')
      .join('Type_Of_User', 'Users.Type_Of_User_uuid_type_of_users', '=', 'Type_Of_User.pk_type_of_user')
      .where({
        pk_uuid
      })
      .then(r =>  req.userLogged = r[0])
      .catch(err => res.send(err))
  }
  next()

  console.log(req.userLogged)
})

// set routes
app.use(routes)


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404))
})

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message
  res.locals.error = req.app.get('env') === 'development' ? err : {}

  // render the error page
  res.status(err.status || 500)
  res.render('error')
})

module.exports = app
