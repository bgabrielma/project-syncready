var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {

  const users = [
    { 
      name: "Bruno Martins",
      age: 18,
      club: "FC Porto"
    },
    { 
      name: "Pinto da Costa",
      age: 19,
      club: "FC Porto"
    }
  ]
  res.render('index', { users, title: 'Uma página como as outras' });
});

module.exports = router;
