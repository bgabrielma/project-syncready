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
router.post('/register', UserController.register)
router.post('/auth', UserController.auth)

/* dashboard */
router.get('/dashboard', DashboardController.dashboard)
router.get('/logout', DashboardController.logout)

/* Users */
router.get('/dashboard/user/new', DashboardController.newUser)
router.get('/dashboard/user/post', DashboardController.newUser)
router.get('/dashboard/user/list', DashboardController.listUsers)

/* Rooms */
router.get('/dashboard/room/new', DashboardController.newRoom)
router.get('/dashboard/room/list', DashboardController.listRoom)

/* Tickets */
router.get('/dashboard/ticket/new', DashboardController.newTicket)
router.get('/dashboard/ticket/list', DashboardController.listTicket)


/* API REST */
router.post('/user', UserController.post)

router.get('/cookies', function(req, res) {
  res.send(req.cookies)
});

module.exports = router