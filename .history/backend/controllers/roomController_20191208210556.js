const db = require('../config/db')

const ticketController = require('./ticketController')

const saveRoom = async function(req, res) {

  const { nameRoom, description, roomCode, datasheet, members, untilAt } = req.body

  let datasheetUUID = ''

  if(req.file) {

    // create new datasheet

    await db('Datasheet')
      .insert({
        description,
        created_at: db.raw('NOW()'),
        updated_at: db.raw('NOW()'),
        designation: req.file.filename
      })
      .then(async data => {

        // find datasheet UUID
        await db('Datasheet')
          .select('uuid_datasheet')
          .where({ 
            pk_datasheet: data
          })
          .then(data => {
            datasheetUUID = data[0].uuid_datasheet
          })
      })
      .catch(err => {
        return new Error('Error on inserting new file!')
      })
  } else datasheetUUID = datasheet

  // create new room

  // store uuid's room for ticket operation
  let roomUUID = ''

  await db('Rooms')
    .insert({
      room_code: roomCode,
      name_room: nameRoom, 
      created_at: db.raw('NOW()'),
      updated_at: db.raw('NOW()'),
      until_at: untilAt,
      Datasheet_uuid_datasheet: datasheetUUID,
    })
    .then(async data => {

      // get new's uuid room
      await db('Rooms')
        .select('Rooms.uuid_room')
        .where({
          pk_rooms: data
        })
        .then(async data => {
          roomUUID = data[0].uuid_room

          members.forEach(elem => {
            console.log(elem)
          })

          // put users in room
        /*  
          members.forEach(async elem => {
            await db('Users_has_Rooms')
              .insert({
                Users_pk_uuid: elem,
                Rooms_uuid_room: roomUUID
              })
              .catch(err => console.log('Error', err))
          })

          // set user's company entity as an member of this group

          await db('Users')
          .select('pk_uuid')
          .innerJoin('Users_has_Companies', 'Users_has_Companies.Users_pk_uuid', '=', 'Users.pk_uuid')
          .innerJoin('Companies', 'Users_has_Companies.Companies_uuid_company', '=', 'Companies.uuid_company')
          .innerJoin('Type_Of_User', 'Type_Of_User.uuid_type_of_users', '=', 'Users.Type_Of_User_uuid_type_of_users')
          .where('Type_Of_User.type', '=', 'Entidade')
          .then(async data => {

            // avoid system to insert "two times" this user
            if(req.userLogged.pk_uuid !== data[0].pk_uuid) {
              await db('Users_has_Rooms')
                .insert({
                  Users_pk_uuid: data[0].pk_uuid,
                  Rooms_uuid_room: roomUUID
                })
            }
          }) */



        })
    })
    .catch(err => {
      return new Error('Error on inserting new file!')
    })

    // after this whole process xD - create new ticket - final process

    // '1' -> Técnico pendente

    return ticketController.newTicket(req, '1', roomUUID)
}

const post = function(req, res) {
  return res.send(req.body)
}

module.exports = {
  post,
  saveRoom
}