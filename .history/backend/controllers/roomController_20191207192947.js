const saveUser = function(data) {
  return true
}

const post = function(req, res) {
  return res.send(req.body)
}

module.exports = {
  post,
  saveUser
}