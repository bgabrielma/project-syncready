const db = require('../config/db')
const validadorCC = require('../utils/validadorCC')

const messageErrorOnInsert = { message: 'Ocorreu um erro ao realizar o seu pedido - tente mais tarde :(' }

const emptyField = { message: 'Este campo não pode estar vazio!' }
const invalidPass = { message: 'As palavras-passes indicadas não correspondem uma com a outra!' }

const login = async function(email, password, req, res) {
  await db('Users')
  .select('Users.*')
  .join('Type_Of_User', 'Users.Type_Of_User_uuid_type_of_users', '=', 'Type_Of_User.uuid_type_of_users')
  .where({
      email,
      password,
    })
  .whereNot('Type_Of_User.type', 'Cliente')
    .then(r => {
      if(r.length !== 0) {
        res.cookie('SYNCREADY_COOKIE_LOGIN', r[0].pk_uuid, { maxAge: 72000000, httpOnly: true })
        return res.redirect(`/dashboard`)
      } else {
        messageErrorOnInsert.message = 'Email e/ou password inválido(s)!'
        return res.render('index', { title: 'SyncReady', page: 'main/login', errors: { }, messageErrorOnInsert } )
      }
    })
    .catch(err => {
      return res.render('index', { title: 'SyncReady', page: 'main/login', errors: { }, messageErrorOnInsert } )
    })
}

const validateNewUser = async function(req) {
  let errors = {}
  const { fullname, address, email, contacto, cc, username, password, repeatpassword, companyCode } = req.body

  if (!fullname) errors.fullname = emptyField
  if (!address) errors.address = emptyField 
   if (email) {
    await db('Users').count('email', { as: 'count' })
      .where('email', email)
      .then(r => { 
        if (r[0].count) {
          errors.email = { message: 'O email indicado já pertence a outra conta!'}
        }
      })
      .catch(_ => {
        errors.errorOnInsert = messageErrorOnInsert
      })
  } else errors.email = emptyField

  if (!cc || !validadorCC(cc)) errors.cc = { message: 'O número de cartão de cidadão é inválido!' }
  if (!contacto || contacto.length !== 9) errors.contacto = { message: 'O número de telefone é inválido!' }

  if (username) {
    await db('Users').count('nickname', { as: 'count' })
      .where('nickname', username)
      .then(r => { 
        if (r[0].count) {
          errors.username = { message: 'O nome de utilizador indicado já está em uso!'}
        }
      })
      .catch(_ => {
        errors.errorOnInsert = messageErrorOnInsert
      })
  } else errors.username = emptyField

  if (companyCode) {
    await db('Companies').count('uuid_company', { as: 'count' })
      .where('uuid_company', companyCode)
      .then(r => {
        if (!r[0].count) {
          errors.companyCode = { message: 'O código da empresa é inválida!' }
        }
      })
      .catch(_ => {
        errors.errorOnInsert = messageErrorOnInsert
      })

  } else errors.companyCode = emptyField

  if (!password) errors.password = emptyField
  if (!repeatpassword) errors.repeatpassword = emptyField
  
  if (password != repeatpassword) {
    errors.repeatpassword = invalidPass
    errors.password = invalidPass
  }
  
  return {
    errors,
    predata: req.body
  }
}

const registerToCompany = async function (uuid, companyCode) {
  return db('Users_has_Companies')
      .insert({
        Users_pk_uuid: uuid,
        Companies_uuid_company: companyCode
      })
}

const findUUIDByID = async function(id) {
  return db('Users')
      .select('pk_uuid')
      .where('pk_user', id)
}

const findCompanyByUUID = async function(uuid) {
  return db('Users_has_Companies')
    .join('Companies', 'Users_has_Companies.Companies_uuid_company', '=', 'Companies.uuid_company')
    .where('Users_pk_uuid', uuid)
}

const getUserType = async function(companyCode) {
  return db('Type_Of_User')
  .where({
    type: !!companyCode ? 'Técnico' : 'Cliente'
  })
}

const register = async function(req, res) {
  const { errors, predata } = await validateNewUser(req)

  if (Object.keys(errors).length > 0) 
    return res.render('index', { title: 'SyncReady', page: 'main/register', errors , predata } )
  else {
    
    await saveUser(req)
      .then(async (responseOne) => { 

        // getting uuid
        let uuid = {}

        await findUUIDByID(responseOne[0])
          .then(response2 => {
            uuid = response2[0].pk_uuid
          })
          
        // Insert to company
        await registerToCompany(uuid, req.body.companyCode)
          .then(_ => login(predata.email, predata.password, req, res))
          .catch(_ => {
            errors.errorOnInsert = messageErrorOnInsert
            return res.render('index', { title: 'SyncReady', page: 'main/register', errors , predata: { } } )
          })
      })
      .catch(_ => {
        errors.errorOnInsert = messageErrorOnInsert
        return res.render('index', { title: 'SyncReady', page: 'main/register', errors , predata: { } } )
      })



    // Login username
    // login(predata.email, predata.password, req, res)
  }
}

const auth = function(req, res) {
  let errors = {}
  const emptyField = { message: 'Este campo não pode estar vazio!' }
  const { email, password } = req.body

  if (!email) errors.email = emptyField
  if (!password) errors.password = emptyField

  if (Object.keys(errors).length > 0) 
    return res.render('index', { title: 'SyncReady', page: 'main/login', errors, messageErrorOnInsert: null })
  else 
    login(email, password, req, res) 
}

const saveUser = async function(req) {
  const { fullname, address, email, contacto, cc, username, password, companyCode } = req.body
  
  let typeOfUserUUID = null

  await getUserType(companyCode)
    .then(res => typeOfUserUUID = res[0].uuid_type_of_users)

  return db('Users')
    .insert({
    nickname: username,
    fullname,
    address, 
    email,
    telephone: contacto,
    citizencard: cc,
    password,
    Type_Of_User_uuid_type_of_users: typeOfUserUUID
  })
}

const updateUser = async function(req) {
  let user = {}

  await db('Users')
    .where({
      pk_uuid: req.body.secretUUIDToUpdate
    })
    .then(data => {
      user = data[0]
    })
    .catch(err => {
      throw err
    })

    console.log("AAA")

  const { fullname, address, email, contacto, cc, username, password, type, companyCode, secretUUIDToUpdate } = req.body

  return db('Users')
    .update({
    fullname,
    address, 
    email,
    telephone: contacto,
    citizencard: cc,
    Type_Of_User_uuid_type_of_users: typeOfUserUUID,
    password: password === "" ? user.password : password,
    update_at: db.raw('NOW()')
    })
    .where({ pk_uuid: req.body.secretUUIDToUpdate })
}

/* API REST */
const post = async function(req, res) {

  if(!req.body.secretUUIDToUpdate) {
    const { errors } = await validateNewUser(req)

    if(Object.keys(errors).length > 0) {
      return res.status(401).send(errors)
    } 
    await saveUser(req)
      .then(data => res.status(200).send({ lastIdInserted: data[0], ok: true }))
      .catch(err => res.status(500).send({ err, ok: false }))

      return
  }

  // update
  await updateUser(req)
    .then(_ => res.status(200).send({ ok: true }))
}

const get = async function(req, res) {
  const instance = db('Users')

  if (req.query.uuid)
    instance.where({ pk_uuid: req.query.uuid })

  instance
    .then(data => res.status(200).send(data))
    .catch(err => res.status(500).send(err))
}

const del = async function(req, res) {
  const { uuid: pk_uuid } = req.body
  
  await db('Users')
    .where({ pk_uuid })
    .del()
    .then(res.send({ ok: true }))
    .catch(err => res.send({ err, ok: false }))
}

module.exports = {
  register,
  auth,
  get,
  post,
  del,
  validateNewUser,
  saveUser,
  messageErrorOnInsert,
  findCompanyByUUID,
  registerToCompany,
  findUUIDByID
}