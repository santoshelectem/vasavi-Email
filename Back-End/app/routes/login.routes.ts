import express from 'express'
import {
  findLoginByItemId,
  fetchAlllogins,
  loginCreate,
  deleteLogin,
  update
} from '../controllers/login.controller'
module.exports = app => {
  const router = express.Router()

  router.get('/login/:id', findLoginByItemId)
  router.get('/login', fetchAlllogins)
  router.post('/postlogin', loginCreate)
  router.delete('/deletelogin/:id', deleteLogin)
  router.put('/updateLogin/:id', update)
  app.use('/api/login', router)
}
