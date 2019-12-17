const getUsers = function (req, res, next) {
  console.log(req.get('db'))
  res.send('123')
}

module.exports = { getUsers }