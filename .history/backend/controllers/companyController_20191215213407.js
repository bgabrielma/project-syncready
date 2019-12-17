const db = require('../config/db')

const del = function(req, res) {
  const { uuid: uuid_companny } = req.body
  
  await db('Users')
    .where({ uuid_companny })
    .del()
    .then(res.send({ ok: true }))
    .catch(err => res.send({ err, ok: false }))
}

module.exports = {
  del
}