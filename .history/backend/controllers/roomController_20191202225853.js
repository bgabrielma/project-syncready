const db = require('../config/db')

const randomToken = require('random-token')

const dashController = require('./dashboardController')
const { verifyUser } = dashController


/* API REST */

const post = async function(req, res) {

  res.send(req.body)

}