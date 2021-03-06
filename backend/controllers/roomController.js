const db = require('../config/db')

const ticketController = require('./ticketController')

const saveUsersIntoRoom = function(pk_uuid, room_uuid) {
  return db('Users_has_Rooms')
    .insert({
      Users_pk_uuid: pk_uuid,
      Rooms_uuid_room: room_uuid
    })
}

const addIntoRoom = async function(req, res) {
  const { roomUUID, userUUID } = req.query

  if (!roomUUID || !userUUID) return res.status(401).send({ err: 'Invalid data received' })

  await saveUsersIntoRoom(userUUID, roomUUID)
    .then(_res => res.status(200).send({ ok: true }))
    .catch(err => res.status(500).send(err))
}

const verifyUserInRoom = async function(req, res) {
  const { roomUUID, userUUID } = req.query

  if (!roomUUID || !userUUID) return res.status(401).send({ err: 'Invalid data received' })
  
  await db('Users_has_Rooms')
    .count('*', { as: 'count' })
    .where({
      Users_pk_uuid: userUUID,
      Rooms_uuid_room: roomUUID
    })
    .then(_res => res.status(200).send({ ok: true, count: _res[0].count }))
    .catch(err => res.status(500).send(err))
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
    .update({
      name_room: nameRoom, 
      updated_at: db.raw('NOW()'),
      until_at: untilAtRoom,
    })
    .where('Rooms.uuid_room', '=', `${secretUUIDToUpdate}`)
    .then(async _ => {

      // update status on ticket
      await db('Tickets')
        .update({
          Ticket_Options_uuid_ticket_options: statusRoom
        })
        .where('Tickets.Rooms_uuid_room', '=', `${secretUUIDToUpdate}`)

      // update datasheet
        
      // first, get uuid datasheet from uuid room
      await db('Rooms')
        .select('Rooms.Datasheet_uuid_datasheet')
        .where('Rooms.uuid_room', '=', `${secretUUIDToUpdate}`)
        .then(async datasheet => {
          await db('Datasheet')
          .update({
            description: descriptionRoom
          })
          .where('uuid_datasheet', '=', `${datasheet[0].Datasheet_uuid_datasheet}`)
        })

      // update User_has_Rooms

      // first, delete all rows with existing members
      await db('Users_has_Rooms')
        .del()
        .where({
          Rooms_uuid_room: secretUUIDToUpdate
        })

      // and then insert them all again
      membersRoom.forEach(async memberRoom => {
        await db('Users_has_Rooms')
          .insert({
            Users_pk_uuid: memberRoom,
            Rooms_uuid_room: secretUUIDToUpdate
          })
      })
        
      res.status(200).send({ ok: true })
    })
}

const get = function(req, res) {
  const instance = db('Rooms')
    .select('Rooms.*', 'Datasheet.designation', 'Datasheet.description', 'Ticket_Options.ticket_option_designation', 'Ticket_Options.uuid_ticket_options')
    .innerJoin('Datasheet', 'Datasheet.uuid_datasheet', '=', 'Rooms.Datasheet_uuid_datasheet')
    .innerJoin('Tickets', 'Tickets.Rooms_uuid_room', '=', 'Rooms.uuid_room')
    .innerJoin('Ticket_Options', 'Ticket_Options.uuid_ticket_options', '=', 'Tickets.Ticket_Options_uuid_ticket_options')

  if (req.query.roomCode)
    instance.andWhere({ room_code: req.query.roomCode })

  else if (req.query.roomUUID)
    instance.andWhere({ uuid_room: req.query.roomUUID })

  else if (req.query.userUUID)
    instance
      .innerJoin('Users_has_Rooms', 'Rooms.uuid_room', 'Users_has_Rooms.Rooms_uuid_room')
      .andWhere({ 'Users_has_Rooms.Users_pk_uuid': req.query.userUUID })

  instance
    .then(data => res.status(200).send({ ok: true, response: data }))
    .catch(err => res.status(500).send(err))
}

async function updateRoomImage(req, res) {
  const { image, roomUUID } = req.query

  await db('Rooms')
    .update({
      image
    })
    .where({ uuid_room: roomUUID })
    .then(_ => res.status(200).send({ ok: true }))
    .catch(err => res.status(500).send({ err, ok: false }))
}


module.exports = {
  get,
  post,
  saveRoom,
  verifyUserInRoom,
  addIntoRoom,
  updateRoomImage
}