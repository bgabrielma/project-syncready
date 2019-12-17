const createError = require('http-errors')

const express = require('express')
const routes = require('./config/routes')

const path = require('path')
const cookieParser = require('cookie-parser')
const logger = require('morgan')

const db = require('./config/db')

const app = express()

// Linking db object to all app, setting a new variable
app.set('db', db);

// view engine setup
app.set('views', path.join(__dirname, 'views'))
app.set('view engine', 'ejs')

app.use(logger('dev'))
app.use(express.json())
app.use(express.urlencoded({ extended: false }))
app.use(cookieParser())
app.use(express.static(path.join(__dirname, 'public')))

// middleware routes
app.use(routes);

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
