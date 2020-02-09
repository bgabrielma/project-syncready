const db = require('../config/db')

const get = async (req, res) => {

  const instance = db('Type_Of_User_has_Alerts')

  if (req.query.uuid) {
    instance
      .where({
        Alerts_uuid_alerts: req.query.uuid
      })
  }

  await instance
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