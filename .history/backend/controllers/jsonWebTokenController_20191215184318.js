const jwt = require('jsonwebtoken')

const secret = 'SYNC4%8gv!!!$xa-+jr5$4b^-d(#bpj8(=n0oacld=0$3m-w3ftvvkREADY'

const generate = function(payload) {
  return jwt.sign({ data: payload }, secret, { expiresIn: '7d' })
}

const verify = function(token) {
  let valid = false
  try {
    _ = jwt.verify(token, secret)
    valid = true
  } catch(err) {
    valid = false
  }

  return valid
}

const decode = function(token) {
  jwt.verify(token, 'shhhhh', function(err, decoded) {
    console.log(decoded.foo) // bar
  });
}

module.exports = {
  generate,
  verify
}