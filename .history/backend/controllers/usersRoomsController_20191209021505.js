const db = require('../config/db')

const get = async function(req, res) {
  const instance = db('Users_has_Rooms')

  if (req.query.uuid)
    instance.where({ pk_uuid: req.query.uuid })

  instance
    .then(data => res.status(200).send(data))
    .catch(err => res.status(500).send(err))
}

module.exports = {

}