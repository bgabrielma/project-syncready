const express = require('express');
const router = express.Router();

const IndexController = require('./controllers/usersController');

router.get('/', (req, res, next) => {
  res.render('index', { title: 'SyncReady' })
});

module.exports = router;