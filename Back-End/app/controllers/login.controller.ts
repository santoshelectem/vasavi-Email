import db from '../models'
import Sequelize from 'sequelize'
import sequelize from '../config/seq.config.js'
const Login = db.login
db.sequelize = sequelize
db.Sequelize = Sequelize

export async function loginCreate(req: Request, res: Response) {
  const login = {
    Username: req.body.Username,
    Email: req.body.Email,
    clientFk: req.body.clientFk,
    planFk: req.body.planFk
  }

  const Login = await db.login
  let query = `INSERT INTO logins ("Username", "Email", "clientFk","planFk","createdAt", "updatedAt")
  VALUES ('${login.Username}', '${login.Email}', '${login.clientFk}','${login.planFk}','2021-11-03 16:24:24','2021-11-03 16:24:24');`
  await sequelize.query(query, {type: sequelize.QueryTypes.INSERT})

  try {
    // const newItem = await UserService.create(user);
    res.status(201).json(login)
  } catch (e) {
    res.status(500).send('error.message not inserted')
  }
  return res.json({
    message: 'Post Created'
  })
}

export async function findLoginByItemId(req: Request, res: Response) {
  const id = req.params.id
  const conn = await db.login

  let query = `SELECT * FROM logins where id='${id}';`
  await sequelize.query(query, {type: sequelize.QueryTypes.SELECT})
  try {
    if (login) {
      return res.status(200).send(login)
    }
    res.status(404).send('user not found')
  } catch (e) {
    res.status(500).send('usernot message')
  }
  return res.json({message: 'get Created'})
}

export async function fetchAlllogins(
  req: Request,
  res: Response
): Promise<Response> {
  const conn = await db.login
  let query = `SELECT * FROM logins;`
  const login = await sequelize.query(query, {
    type: sequelize.QueryTypes.SELECT
  })
  try {
    //  const users: BaseUser[] = await UserService.findAll();
    res.status(200).send(login)
  } catch (e) {
    res.status(500).send('notsended')
  }
  return res.json(login[0])
}

export async function update(req: Request, res: Response) {
  const id = req.params.id
  const loginupadate = {
    Username: req.body.Username,
    Email: req.body.Email,
    clientFk: req.body.clientFk,
    planFk: req.body.planFk
  }
  const conn = await db.login

  let query = `UPDATE logins SET "Username" = '${loginupadate.Username}',"Email"= '$${loginupadate.Email}',"clientFk"= '${loginupadate.clientFk}',"planFk"= '${loginupadate.planFk}' WHERE id = ${id};`
  await sequelize.query(query, {type: sequelize.QueryTypes.UPDATE})
  try {
    if (loginupadate) {
      return res.status(200).json(loginupadate)
    }
    //const newUser = await UserService.create(userUpdate);
    // res.status(201).json('not created');
  } catch (e) {
    res.status(500).send('error.message')
  }
  return res.json({
    message: 'Post updated'
  })
}

export async function deleteLogin(req: Request, res: Response) {
  const id = req.params.id
  let query = `DELETE FROM logins WHERE id = '${id}';`
  await sequelize.query(query, {type: sequelize.QueryTypes.DELETE})
  try {
    res.sendStatus(204)
  } catch (e) {
    res.status(500).send('e.message')
  }
  return res.json({
    message: 'LOGIN deleted'
  })
}
