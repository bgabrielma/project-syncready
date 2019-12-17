const express = require('express');
const router = express.Router();

const UsersController = require('./controllers/usersController');

Router.post('/users', UsersController.CreateUser);

module.exports = Router;