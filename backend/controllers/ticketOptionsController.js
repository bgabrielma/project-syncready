const db = require('../config/db')

/* API REST */

const get = async function(req, res) {
  await db('Ticket_Options')
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