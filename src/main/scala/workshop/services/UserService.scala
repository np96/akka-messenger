package workshop.services

import slick.jdbc.{JdbcBackend, JdbcProfile}
import workshop.User
import workshop.common.concurrent.ApplicationContext
import workshop.common.db.{EntityAlreadyExistsException, EntityIsNotExistsException}
import workshop.common.db.models.UserModel

import scala.concurrent.Future

trait UserService {
  def findUser(login: String): Future[Option[User]]
  def createUser(login: String, firstName: Option[String], lastName: Option[String], status: Option[String]): Future[User]
  def changeUserStatus(login: String, status: Option[String]): Future[User]
}

class UserServiceImpl(val profile: JdbcProfile, db: JdbcBackend.Database) extends UserService with ApplicationContext with UserModel {
  import profile.api._

  override def findUser(login: String): Future[Option[User]] = {
    db.run {
      users.filter(_.login === login.bind).result.headOption
    }
  }

  override def createUser(login: String, firstName: Option[String], lastName: Option[String], status: Option[String]): Future[User] = {
    val user = User(login, firstName, lastName, status)

    db.run {
      users.filter(_.login === login.bind).result.headOption.flatMap {
        case None => users += user
        case _ => DBIO.failed(new EntityAlreadyExistsException(s"User login=$login is already exists"))
      }.map(_ => user)
    }
  }

  override def changeUserStatus(login: String, status: Option[String]): Future[User] = {
    for {
      updated <- db.run(users.filter(_.login === login.bind).map(_.status).update(status))
      user <- db.run {
        updated match {
          case 1 => users.filter(_.login === login.bind).result.head
          case _ => DBIO.failed(new EntityIsNotExistsException(s"Cannot find user with login=$login"))
        }
      }
    } yield user
  }
}