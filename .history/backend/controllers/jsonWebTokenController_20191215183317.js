const jwt = require('jsonwebtoken')

const generate = function(payload) {
  const newToken = jwt.sign({ data: payload }, 'secret', { expiresIn: '7d' })

  console.log(newToken)
}