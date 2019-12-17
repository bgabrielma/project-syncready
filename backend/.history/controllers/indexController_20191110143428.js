const getUsers = function(req, res, next) {
  res.render('index', { title: 'SyncReady' });
}

module.exports = {
  getUsers
}