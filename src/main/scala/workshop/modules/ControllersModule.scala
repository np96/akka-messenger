package workshop.modules

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.softwaremill.macwire._
import workshop.server.Controller
import workshop.server.controllers.{ChannelController, UserController}

trait ControllersModule { _: ServiceModule with AkkaModule =>
  lazy val userController: UserController = wire[UserController]
  lazy val channelController: ChannelController = wire[ChannelController]

  lazy val routes: Route = wireSet[Controller].foldLeft[Route](reject)(_ ~ _.routes)
}
