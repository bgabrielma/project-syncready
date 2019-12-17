const db = require('../config/db')
const randomToken = require('random-token')

const dashController = require('./dashboardController')

function verifyUser(req) {
  return !!req.userLogged
}