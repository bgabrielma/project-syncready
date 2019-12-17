const express = require('express');
const router = express.Router();

const loginController = require('../controllers/loginController');
const userController = require('../controllers/userController');

router.get('/', loginController.login);

router.get('/user', )

module.exports = router;