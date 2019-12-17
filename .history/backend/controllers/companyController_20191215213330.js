const db = require('../config/db')

const del = function(req, res) {
  const { uuid: pk_uuid } = req.body
  
  await db('Users')
    .where({ pk_uuid })
    .del()
    .then(res.send({ ok: true }))
    .catch(err => res.send({ err, ok: false }))
}

module.exports = {
  del
}