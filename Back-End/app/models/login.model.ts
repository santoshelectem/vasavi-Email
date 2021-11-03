import {Sequelize} from 'sequelize/types'
import {login} from '.'
import {Plan} from '../models/plan.model'
module.exports = (sequelize, Sequelize) => {
  const Login = sequelize.define('login', {
    Username: {
      type: Sequelize.STRING
    },
    Email: {
      type: Sequelize.STRING
    },
    planFk: {
      type: Sequelize.INTEGER,
      allowNull: true,
      references: {
        model: 'plans',
        key: 'id'
      }
    },
    clientFk: {
      type: Sequelize.INTEGER,
      allowNull: true,
      references: {
        model: 'clients',
        key: 'id'
      }
    }
  })

  Login.associate = function (models) {
    Login.hasMany(models.User, {as: 'user'})
  }
  Login.associate = models => {
    Login.belongsTo(models.Client, {foreignKey: 'client_fk', as: 'client'})
  }
  Login.associate = models => {
    Login.belongsToMany(models.Plan, {
      through: 'LoginPlan',
      as: 'plans',
      foreignKey: 'id'
    })
  }
  return Login
}
