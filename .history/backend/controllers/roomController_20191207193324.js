const saveUser = function(req, filename, res) {
  return res.send(filename)
}

const post = function(req, res) {
  return res.send(req.body)
}

module.exports = {
  post,
  saveUser
}