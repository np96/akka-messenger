package workshop.server.controllers

import akka.http.scaladsl.server.Route
import workshop.User
import workshop.server.Controller
import workshop.services.UserService

object UserController {
  case class CreateUserRequest(login: String, firstName: Option[String], lastName: Option[String], status: Option[String])
  case class CreateUserResponse(user: User)

  case class ChangeUserStatusRequest(status: Option[String])
  case class ChangeUserStatusResponse(user: User)
}

class UserController(userService: UserService) extends Controller {
  override def routes: Route =  changeUserStatusRoute ~ createUserRoute

  private val createUserRoute  = (path("users") & post & entity(as[UserController.CreateUserRequest])) { request =>
    userService.createUser(request.login, request.firstName, request.lastName, request.status).map(UserController.CreateUserResponse)
  }

  private val changeUserStatusRoute = (path("users" / Segment / "status") & post & entity(as[UserController.ChangeUserStatusRequest])) { (login, request) =>
    userService.changeUserStatus(login, request.status).map(UserController.ChangeUserStatusResponse)
  }
}