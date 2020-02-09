const db = require('../config/db')

const get = async (req, res) => {
  await db('Type_Of_Alert')
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