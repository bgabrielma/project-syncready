const db = require('../config/db')
const userController = require('./userController')

const del = async function(req, res) {
  const { uuid: uuid_company } = req.body
  
  await db('Companies')
    .where({ uuid_company })
    .del()
    .then(_ => {

      let userUUID = ''
      await db('Users_has_Companies')
        .select('Users_pk_uuid')
        .where('Users_has_Companies.Companies_uuid_company', '=', `${ uuid_company }`)
        .then(response => userUUID = response[0].Users_pk_uuid)
    })
    .catch(err => res.send({ err, ok: false }))
}

module.exports = {
  del
}