const express = require('express');
const router = express.Router();

//const UsersController = require('./controllers/usersController');

router.post('/', (req, res, next) => {
  res.render('index', { title: 'SyncReady' })
});

module.exports = Router;