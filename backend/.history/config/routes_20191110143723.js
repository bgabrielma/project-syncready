var express = require('express');
var router = express.Router();

/* Controllers */
const IndexController = require('../controllers/indexController')
const userController = require('../controllers/userController')

/* index */
router.get('/', IndexController.login)

/* user */
router.get('/user', IndexController.login)


module.exports = router