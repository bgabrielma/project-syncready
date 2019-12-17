const express = require('express');
const router = express.Router();

/* Controllers */
const IndexController = require('../controllers/indexController')
const UserController = require('../controllers/userController')
const DashboardController = require('../controllers/dashboardController')

/* index */
router.get('/', IndexController.loginOrRegister)
router.get('/checkdb', IndexController.checkdb)
router.get('/main', IndexController.main)

/* user */
router.get('/user', UserController.getUsers)
router.post('/validateRegister', UserController.validateRegister)

/* dashboard */
router.get('/dashboard', DashboardController.dashboard)

module.exports = router