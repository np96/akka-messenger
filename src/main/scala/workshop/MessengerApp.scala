package workshop

import akka.http.scaladsl.Http
import org.slf4j.LoggerFactory
import workshop.common.concurrent.ApplicationContext
import workshop.modules._

trait Dependencies extends AkkaModule with DatabaseModule with ServiceModule with ControllersModule with ConfigModule

object MessengerApp extends App with Dependencies with ApplicationContext {
  val logger = LoggerFactory.getLogger("app")

  val (host, port) = config.getString("http.host") -> config.getInt("http.port")
  val binding = Http().bindAndHandle(routes, host, port)
  logger.info(s"Server started at $host:$port")

  sys.addShutdownHook {
    binding.flatMap(_.unbind()).flatMap(_ => system.terminate()).map(_ => logger.info("Server stopped"))
  }
}