var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  const payload = {}

  res.render('index', { payload });
});

module.exports = router;
