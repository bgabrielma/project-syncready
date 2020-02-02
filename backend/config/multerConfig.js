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
        if (err) {
          cb(err);
          console.log("err", err);
        }
        const fileName = `${hash.toString('hex')}-${file.originalname}`;
        cb(null, fileName);
      })
    }, 
  }),
  limits: {
    fileSize: 25 * 1024 * 1024
  },
  fileFilter: (req, file, cb) => {
    const allowedMimes = [
      'application/pdf',
      'application/docx',
      'application/dotx',
      'application/doc',
      'application/dot',
      'image/gif',
      'image/png',
      'image/jpeg',
      'image/jpg',
      'image/bmp',
      'image/webp'
    ]

    if (allowedMimes.includes(file.mimetype)) {
      cb(null, true)
    } else {
      cb(new Error('Formato de ficheiro inválido!'))
    }
  }
 }