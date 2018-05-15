package workshop.server.controllers

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import workshop.User
import workshop.services.UserService

import scala.concurrent.Future

class UserControllerTest extends ControllerTest {
  behavior of "UserController"

  trait Setup {
    val userService: UserService = mock[UserService]
    lazy val controller = new UserController(userService)
    lazy val routes: Route = Route.seal(controller.routes)
  }

  "POST /users" should "create new user" in new Setup {
    val login = "test-login"
    val firstName = Some("John")
    val lastName = Some("Doe")
    val userStatus = Some("At work")

    (userService.createUser _).expects(login, firstName, lastName, userStatus).returning(Future.successful(User(login, firstName, lastName, userStatus)))

    Post[UserController.CreateUserRequest]("/users", UserController.CreateUserRequest(login, firstName, lastName, userStatus)) ~> routes ~> check {
      status shouldBe StatusCodes.OK
      entityAs[UserController.CreateUserResponse] shouldBe UserController.CreateUserResponse(User(
        login = login,
        firstName = firstName,
        lastName = lastName,
        status = userStatus
      ))
    }
  }

  "POST /users/{id}/status" should "update user status" in new Setup {
    val login = "test-login"
    val firstName = Some("John")
    val lastName = Some("Doe")
    val userStatus = Some("At work")

    (userService.changeUserStatus _).expects(login, userStatus).returning(Future.successful(User(login, firstName, lastName, userStatus)))

    Post[UserController.ChangeUserStatusRequest](s"/users/$login/status", UserController.ChangeUserStatusRequest(userStatus)) ~> routes ~> check {
      status shouldBe StatusCodes.OK

      entityAs[UserController.ChangeUserStatusResponse] shouldBe UserController.ChangeUserStatusResponse(User(
        login = login,
        firstName = firstName,
        lastName = lastName,
        status = userStatus
      ))
    }
  }
}