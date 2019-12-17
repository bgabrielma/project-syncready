const express = require('express')
const router = express.Router()

const multer = require('multer')
const multerConfig = require('./multerConfig')

/* Controllers */
const IndexController = require('../controllers/indexController')
const UserController = require('../controllers/userController')
const DashboardController = require('../controllers/dashboardController')
const TypeUserController = require('../controllers/typeUserController')
const RoomController = require('../controllers/roomController')

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
router.post('/dashboard/user/new', DashboardController.registerUser)
router.get('/dashboard/user/list', DashboardController.listUsers)

/* Rooms */
router.get('/dashboard/room/new', DashboardController.newRoom)
router.get('/dashboard/room/list', DashboardController.listRoom)
router.post('/dashboard/room/new', multer(multerConfig).single('newFile'), DashboardController.registerRoom)

/* Tickets */
router.get('/dashboard/ticket/new', DashboardController.newTicket)
router.get('/dashboard/ticket/list', DashboardController.listTicket)

/* Alerts */
router.get('/dashboard/alert/new', DashboardController.newAlert)
router.get('/dashboard/alert/list', DashboardController.listAlert)

/* API REST */

// user
router.get('/user', UserController.get)
router.post('/user', UserController.post)
router.delete('/user', UserController.del)

// type user
router.get('/user/type', TypeUserController.get)

// room
router.post('/room', multer(multerConfig).single('newFile'), function(req, res) {
  const fileinfo = req.file;
  res.send(req.body)
})

router.get('/cookies', function(req, res) {
  res.send(req.cookies)
});

module.exports = router