const multer = require('multer')


/**
 * Multer configuration
 */

const storage = multer.diskStorage({
  destination: function(req, file, cb) {
    cb(null, 'uploads/')
  },
  filename: function(req, file, cb) {
    cb(null, Date.now() + file.originalname)
  }
})