const post = function(req, res) {
  return res.send(req.params)
}

module.exports = {
  post
}