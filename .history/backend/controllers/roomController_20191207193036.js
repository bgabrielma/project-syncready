const saveUser = function(data) {
  return res.send(data)
}

const post = function(req, res) {
  return res.send(req.body)
}

module.exports = {
  post,
  saveUser
}