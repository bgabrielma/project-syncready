const jwt = require('jsonwebtoken')

const generate = function(payload) {
  return jwt.sign({ data: payload }, `SYNCREADY_TOKEN_FOR_${payload.username}`, { expiresIn: '7d' })
}

const isValid = function(token) {
  try {

  } catch(err) {
    
  }
}