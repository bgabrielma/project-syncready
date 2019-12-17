const multer = require('multer')
const path = require('path')
const crypto = require('crypto')


/**
 * Multer configuration
 */

 module.exports = {
  dest: path.resolve(__dirname, '..', 'uploads'),
  storage: multer.diskStorage({  

  }),
  limits: {
    fileSize: 2 * 1024 * 1024
  },
  fileFilter: (req, file, cb) => {
    const allowedMimes = [
      'application/pdf'
    ]
  }
 }