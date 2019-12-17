var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  console.log(res.get('db'))
  res.send('123')
});

module.exports = router;
