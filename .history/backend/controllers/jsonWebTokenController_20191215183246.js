const jwt = require('jsonwebtoken')

const generate = function(payload) {
  const newToken = jwt.sign({ data: 'foobar' }, 'secret', { expiresIn: '30d' });
}