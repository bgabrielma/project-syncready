const db = require('../config/db')

/* API REST */

const get = function(req, res) {
  await db('Type_Of_User')
    .then(data => {
      return res.send(data)
    })
}

module.exports = {
  get
}