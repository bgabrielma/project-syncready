const express = require('express');
const router = express.Router();
const db = require('./db')

/* Controllers */
const IndexController = require('../controllers/indexController')
const userController = require('../controllers/userController')

module.exports = app => {
  /* index */
  router.get('/', IndexController.login)

  /* user */
  router.get('/user', userController.getUsers)
}