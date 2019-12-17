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

/* Users */
router.get('/dashboard/user/new', DashboardController.newUser)
router.get('/dashboard/user/list', DashboardController.listUsers)

/* Rooms */
router.get('/dashboard/room/new', DashboardController.newRoom)
router.get('/dashboard/room/list', DashboardController.listRoom)

router.get('/cookies', function(req, res) {
  const username = req.cookies['username'];
  if (username) {
      return res.send(username);        
  }

  return res.send('No cookie found');
});

module.exports = router