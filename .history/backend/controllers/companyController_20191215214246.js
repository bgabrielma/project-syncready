const db = require('../config/db')
const userController = require('./userController')

const del = async function(req, res) {
  const { uuid: uuid_company } = req.body
  
  await db('Companies')
    .where({ uuid_company })
    .del()
    .then(response => {

      let userUUID = ''
      await db('Users_has_Companies')
        .select('Users_pk_uuid')
        .where('Users_has_Companies.Companies_uuid_company', '=', '')

    })
    .catch(err => res.send({ err, ok: false }))
}

module.exports = {
  del
}