const jwt = require('jsonwebtoken')

const generate = function(payload) {
  return jwt.sign({ data: payload }, `SYNCREADY_TOKEN_GENERATOR`, { expiresIn: '7d' })
}

const isValid = function(token) {
  try {
    let decoded = jwt.verify('token', `SYNCREADY_FOR`)
  } catch(err) {

  }
}