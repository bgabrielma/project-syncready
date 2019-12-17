const db = require('../config/db')

/* API REST */

const get = function(req, res) {
  await db('Type_Of_User')
    .then(data => {
      return res.status(200).send(data)
    })
    .catch(err => {
      return res.status(500).send(err)
    })
}

module.exports = {
  get
}