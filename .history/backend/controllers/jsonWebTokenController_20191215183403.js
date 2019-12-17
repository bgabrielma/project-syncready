const jwt = require('jsonwebtoken')

const generate = function(payload) {
  const newToken = jwt.sign({ data: payload }, `SYNCREADY_TOKEN_FOR_${payload.username}`, { expiresIn: '7d' })
}