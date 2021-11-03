import db from '../models'
import Sequelize from 'sequelize'
import sequelize from '../config/seq.config.js'
const Login = db.login
db.sequelize = sequelize
db.Sequelize = Sequelize
exports.loginCreate = req => {
  const login = {
    Username: req.body.Username,
    Email: req.body.Email,
    clientFk: req.body.clientFk,
    planFk: req.body.planFk
  }
  Login.create(login)
}
exports.findLoginByItemId = (req, res) => {
  const id = req.params.id
  Login.findByPk(id)
    .then(data => {
      // data.dataValues.attributes =JSON.parse(data.dataValues.attributes);
      res.send(data)
    })
    .catch(err => {
      res.status(500).send({
        message: 'Error retrieving Tutorial with id=' + err
      })
    })
}
exports.fetchAlllogins = (req, res) => {
  let query = 'SELECT * FROM logins'
  sequelize
    .query(query, {type: sequelize.QueryTypes.SELECT})
    .then(data => {
      res.send(data)
    })
    .catch(err => {
      res.status(500).send({
        message: 'Error retrieving logins' + err
      })
    })
}
exports.deleteLogin = (req, res) => {
  const id = req.params.id
  Login.destroy({
    where: {id: id}
  }).catch(err => {
    res.status(500).send({
      message: 'Could not delete Tutorial with id=' + errs
    })
  })
}
exports.update = req => {
  const id = req.params.id
  Login.update(req.body, {
    where: {id: id}
  })
}
