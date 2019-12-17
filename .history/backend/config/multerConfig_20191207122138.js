const multer = require('multer')
const path = require('path')
const crypto = require('crypto')


/**
 * Multer configuration
 */

const dest = path.resolve(__dirname, '..', 'uploads')

 module.exports = {
  storage: multer.diskStorage({  
    destination: (req, file, cb) => {
      cb(null, dest)
    },
    filename: (req, file, cb) => {
      crypto.randomBytes(20, (err, hash) => {
        if (err) cb(err);

        const fileName = `${hash.toString('hex')}-${file.originalname}`;
        cb(null, fileName);
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