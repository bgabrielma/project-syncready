const db = require('../config/db')

const del = async function(req, res) {
  const { uuid: uuid_company } = req.body
  
  await db('Companies')
    .where({ uuid_company })
    .del()
    .then(response => {
      
    })
    .catch(err => res.send({ err, ok: false }))
}

module.exports = {
  del
}