const express = require('express');
const router = express.Router();

/* Controllers */
const IndexController = require('../controllers/indexController')
const UserController = require('../controllers/userController')
const Dashboard = require('../controllers/dashboardController')

/* index */
router.get('/', IndexController.loginOrRegister)
router.get('/checkdb', IndexController.checkdb)
router.get('/main', IndexController.main)

/* user */
router.get('/user', userController.getUsers)

/* dashboard */
router.get('/dashboard', )

module.exports = router