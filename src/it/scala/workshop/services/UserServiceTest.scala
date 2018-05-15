package workshop.services

import slick.jdbc.PostgresProfile
import workshop.User

class UserServiceTest extends BasicTest with DatabaseTest {
  val userService = new UserServiceImpl(PostgresProfile, database)

  behavior of "UserService"

  "UserService.findUser" should "find user" in {
    userService.findUser("test@test.com").futureValue should contain (User("test@test.com", None, None, Some("home")))
  }
}
