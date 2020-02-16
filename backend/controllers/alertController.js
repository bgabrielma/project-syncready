const db = require('../config/db')

const saveUsersTypesIntoAlerts = function(uuid_type_users, uuid_alert) {
  return db('Type_Of_User_has_Alerts')
    .insert({
      Type_Of_User_uuid_type_of_users: uuid_type_users,
      Alerts_uuid_alerts: uuid_alert
    })
}

const del = async (req, res) => {
  const { uuid: uuid_alerts } = req.body

  await db('Alerts')
  .where({
    uuid_alerts
  })
  .del()
  .then(async () => {

    await db('Type_Of_User_has_Alerts')
      .where({
        Alerts_uuid_alerts: uuid_alerts
      })
      .del()
      .then(res.status(200).send({ ok: true }))
  })
  .catch(err => res.status(500).send({ err, ok: false }))
}

const getAlerts = (uuid_alerts = null, uuid_type_of_users = null) => {

  console.log(uuid_alerts)

  const instance = db('Alerts')
    .select('Alerts.*', 'Type_Of_Alert.type_of_alert_designation')
    .innerJoin('Type_Of_Alert', 'Alerts.Type_Of_Alert_uuid_type_of_alert', '=', 'Type_Of_Alert.uuid_type_of_alert')

  if (uuid_alerts) {
    instance
      .where({uuid_alerts})
  }
  
  if (uuid_type_of_users) {
    instance
      .innerJoin('Type_Of_User_has_Alerts', 'Type_Of_User_has_Alerts.Alerts_uuid_alerts', '=', 'Alerts.uuid_alerts')
      .where('Type_Of_User_has_Alerts.Type_Of_User_uuid_type_of_users', '=', uuid_type_of_users)
  }

  return instance
}

const get = async (req, res) => {

  await getAlerts(req.query.uuid, req.query.uuidTypeUsers)
    .then(data => res.status(200).send({ ok: true, response: data }))
    .catch(err => res.status(500).send(err))
}

const post = async (req, res) => {
  const { secretUUIDToUpdate, alertName, alertDesc, alertTypesUsers, alertTypesAlerts } = req.body

  await db('Alerts')
    .update({
      name_alerts: alertName,
      alert_text: alertDesc,
      Type_Of_Alert_uuid_type_of_alert: alertTypesAlerts
    })
    .where('uuid_alerts', '=', secretUUIDToUpdate)
    .then(async () => {
    
      if (alertTypesUsers) {

        // remove all types from this alert
        await db('Type_Of_User_has_Alerts')
          .where('Alerts_uuid_alerts', '=', secretUUIDToUpdate)
          .del()

        alertTypesUsers.forEach(async type_user => {
          await saveUsersTypesIntoAlerts(type_user, secretUUIDToUpdate)
        })

      }
      
    })
    res.status(200).send({ ok: true })
}

module.exports = {
  del,
  get,
  post,
  getAlerts,
  saveUsersTypesIntoAlerts
}