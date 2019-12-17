const saveUser = function(req, res) {
  return res.send(req.body)
}

const post = function(req, res) {
  return res.send(req.body)
}

module.exports = {
  post,
  saveUser
}