const saveUser = function(data) {
  console.log(data)
}

const post = function(req, res) {
  return res.send(req.body)
}

module.exports = {
  post,
  saveUser
}