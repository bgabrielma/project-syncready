const express = require('express');
const router = express.Router();

/* Controllers */
const IndexController = require('../controllers/indexController')
const userController = require('../controllers/userController')

/* index */
router.get('/', IndexController.loginOrRegister)
router.get('/checkdb', IndexController.checkdb)
router.get('/main', IndexController.main)

/* user */
router.get('/user', userController.getUsers)

module.exports = router