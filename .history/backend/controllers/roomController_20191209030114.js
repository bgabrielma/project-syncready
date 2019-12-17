const db = require('../config/db')

const ticketController = require('./ticketController')

const saveUsersIntoRoom = function(pk_uuid, room_uuid) {
  return db('Users_has_Rooms')
    .insert({
      Users_pk_uuid: pk_uuid,
      Rooms_uuid_room: room_uuid
    })
}

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

          // put users in room

          // one elem
          if(typeof(req.body.members) === 'string') 
            await saveUsersIntoRoom(req.body.members, roomUUID)
          else members.forEach(async elem => {
              await saveUsersIntoRoom(elem, roomUUID)
            })

          // set user's company entity as an member of this group

          // avoid system to insert "two times" this user
          if(req.userLogged.pk_uuid !== data[0].pk_uuid) {
            await db('Users')
            .select('pk_uuid')
            .innerJoin('Users_has_Companies', 'Users_has_Companies.Users_pk_uuid', '=', 'Users.pk_uuid')
            .innerJoin('Companies', 'Users_has_Companies.Companies_uuid_company', '=', 'Companies.uuid_company')
            .innerJoin('Type_Of_User', 'Type_Of_User.uuid_type_of_users', '=', 'Users.Type_Of_User_uuid_type_of_users')
            .where('Type_Of_User.type', '=', 'Entidade')
            .then(async data => {
              await db('Users_has_Rooms')
                .insert({
                  Users_pk_uuid: data[0].pk_uuid,
                  Rooms_uuid_room: roomUUID
                })
            })
          }
        })
    })
    .catch(err => {
      return new Error('Error on inserting new file!')
    })

    // after this whole process xD - create new ticket - final process

    // '1' -> Técnico pendente

    return ticketController.newTicket(req, '1', roomUUID)
}

const post = async function(req, res) {

  const { secretUUIDToUpdate, statusRoom, nameRoom, untilAtRoom, descriptionRoom, membersRoom } = req.body
  
  if (!req.body.secretUUIDToUpdate) {
    return
  }

  // update room
  await db('Rooms')
    .insert({
      name_room: nameRoom, 
      updated_at: db.raw('NOW()'),
      until_at: untilAtRoom,
    }).then(_ => {
    })
}

const get = function(req, res) {
  const instance = db('Rooms')
    .select('Rooms.*', 'Datasheet.*', 'Ticket_Options.ticket_option_designation', 'Ticket_Options.uuid_ticket_options')
    .innerJoin('Datasheet', 'Datasheet.uuid_datasheet', '=', 'Rooms.Datasheet_uuid_datasheet')
    .innerJoin('Tickets', 'Tickets.Rooms_uuid_room', '=', 'Rooms.uuid_room')
    .innerJoin('Ticket_Options', 'Ticket_Options.uuid_ticket_options', '=', 'Tickets.Ticket_Options_uuid_ticket_options')

  if (req.query.uuid)
    instance.where({ uuid_room: req.query.uuid })

  instance
    .then(data => res.status(200).send(data))
    .catch(err => res.status(500).send(err))
}

module.exports = {
  get,
  post,
  saveRoom
}