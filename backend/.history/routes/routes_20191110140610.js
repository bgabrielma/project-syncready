const Express = require('express');
const Router = Express.Router();

const UsersController = require('./controllers/usersController');

Router.post('/users', UsersController.CreateUser);

module.exports = Router;