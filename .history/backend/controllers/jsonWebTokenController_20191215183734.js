const jwt = require('jsonwebtoken')

const secret = 'SYNC4%8gv!!!$xa-+jr5$4b^-d(#bpj8(=n0oacld=0$3m-w3ftvvkREADY'

const generate = function(payload) {
  return jwt.sign({ data: payload }, secret, { expiresIn: '7d' })
}

const isValid = function(token) {
  let valid = false
  
  try {
    let decoded = jwt.verify('token', secret)
  } catch(err) {

  }
}