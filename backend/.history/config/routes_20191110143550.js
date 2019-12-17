var express = require('express');
var router = express.Router();

/* Controllers */
const IndexController = require('../controllers/indexController')
const userController = require('../controllers/userController')

router.use('/', IndexController.login)


module.exports = router