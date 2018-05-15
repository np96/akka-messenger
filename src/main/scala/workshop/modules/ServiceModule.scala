package workshop.modules

import com.softwaremill.macwire._
import workshop.services.{UserService, UserServiceImpl}

trait ServiceModule { _: DatabaseModule =>
  lazy val userService: UserService = wire[UserServiceImpl]
}
