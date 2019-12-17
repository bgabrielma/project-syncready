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
const TicketOptionsController = require('../controllers/ticketOptionsController')
const UserRoomController = require('../controllers/usersRoomsController')
const JsonWebTokenController = require('../controllers/jsonWebTokenController')

router.use(function(req, res, next) {
  const access = ['main', 'auth', 'logout', 'register'] // backend

  const urlFormatted = req.originalUrl.split('?').shift().split('/')
  
  if(access.includes(urlFormatted[1]) || req.cookies['SYNCREADY_COOKIE_LOGIN']) next()

})

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
router.post('/dashboard/room/new', multer(multerConfig).single('newFile'), DashboardController.registerRoom)
router.get('/dashboard/room/list', DashboardController.listRoom)

/* Alerts */
router.get('/dashboard/alert/new', DashboardController.newAlert)
router.get('/dashboard/alert/list', DashboardController.listAlert)

/* Companies */
router.get('/dashboard/company/new', DashboardController.newCompany)
router.get('/dashboard/company/list', DashboardController.newCompany)
router.post('/dashboard/company/new', DashboardController.registerCompany)

/* API REST */

// user
router.get('/user', UserController.get)
router.post('/user', UserController.post)
router.delete('/user', UserController.del)

// type user
router.get('/user/type', TypeUserController.get)

// User has rooms
router.get('/user/room', UserRoomController.get)

// room
router.get('/room', RoomController.get)
router.post('/room', RoomController.post)

// ticket option
router.get('/ticket/option/status', TicketOptionsController.get)

router.get('/cookies', function(req, res) {
  res.send(req.cookies)
});

module.exports = router