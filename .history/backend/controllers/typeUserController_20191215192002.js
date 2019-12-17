const db = require('../config/db')

/* API REST */

const get = async function(req, res) {
  await db('Type_Of_User')
    .where('Type_Of_User.type', '!=', 'Entidade')
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