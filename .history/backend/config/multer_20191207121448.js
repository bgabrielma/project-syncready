const multer = require('multer')
const path = require('path')
const crypto = require('crypto')


/**
 * Multer configuration
 */

 module.exports = {
  dest: path.resolve(__dirname, '..', 'uploads'),
  storage: multer.diskStorage({  
    destination: (req, file, cb) => {
    },
    filename: () => {
      crypto.randomBytes(20, (err, hash) => {
        if (err) cb(err);

        const fileName = `${hash.toString('hex')}-${file.o}`
      })
    }, 
  }),
  limits: {
    fileSize: 2 * 1024 * 1024
  },
  fileFilter: (req, file, cb) => {
    const allowedMimes = [
      'application/pdf'
    ]

    if (allowedMimes.includes(file.mimetype)) {
      cb(null, true)
    } else {
      cb(new Error('Formato de ficheiro inválido!'))
    }
  }
 }