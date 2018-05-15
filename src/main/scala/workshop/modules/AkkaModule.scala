package workshop.modules

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}

trait AkkaModule {
  implicit val system: ActorSystem = ActorSystem("workshop")
  implicit val materializer: Materializer = ActorMaterializer()
}
