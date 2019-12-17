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
        .where('Users_has_Companies.Companies_uuid_company', '=', `${ uuid_company }`)
        .del()
    })
    .catch(err => res.send({ err, ok: false }))
}

module.exports = {
  del
}