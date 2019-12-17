const express = require('express');
const router = express.Router();

//const UsersController = require('./controllers/usersController');

router.post('/users', UsersController.CreateUser);

module.exports = Router;