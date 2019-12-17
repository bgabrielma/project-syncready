const createError = require('http-errors')

const express = require('express')
const routes = require('./config/routes')

const path = require('path')
const cookieParser = require('cookie-parser')
const logger = require('morgan')

const helmet = require('helmet')

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


var myLogger = function (req, res, next) {
  console.log('LOGGED')
  next()
}

app.use(myLogger)

module.exports = app
