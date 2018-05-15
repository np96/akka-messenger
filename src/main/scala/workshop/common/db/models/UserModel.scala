package workshop.common.db.models

import workshop.User

trait UserModel extends DatabaseModel {
  import profile.api._

  class Users(tag: Tag) extends Table[User](tag, "users")  {
    def login = column[String]("login", O.PrimaryKey)
    def firstName = column[Option[String]]("first_name")
    def lastName = column[Option[String]]("last_name")
    def status = column[Option[String]]("status")

    override def * = (login, firstName, lastName, status) <> (User.tupled, User.unapply)
  }

  val users = TableQuery[Users]
}
