const db = require('../config/db')

const get = async function(req, res) {
  await db('Users_has_Rooms')
}

module.exports = {

}