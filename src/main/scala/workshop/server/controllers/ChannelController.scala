package workshop.server.controllers

import akka.http.scaladsl.server.Route
import workshop.{Channel, Message}
import workshop.server.Controller

object ChannelController {
  case class CreateChannelRequest(name: String, description: String)
  case class CreateChannelResponse(channel: Channel)

  case class GetChannelResponse(channel: Channel)

  case class JoinChannelRequest(login: String)
  case class JoinChannelResponse()

  case class SendMessageToChannelRequest(login: String, text: String)
  case class SendMessageToChannelResponse()

  case class RenderChannelResponse(channel: Channel, messages: Seq[Message])
}

class ChannelController extends Controller {
  import ChannelController._

  override def routes: Route = channelInfoRoute ~ joinChannelRoute ~ sendMessageToChannelRoute ~ createChannelRoute ~ archiveChannelRoute ~ renderChannelRoute

  private val channelInfoRoute = path(Segment / "info") { channelName =>
    ???
  }
  
  private val archiveChannelRoute = (path(Segment / "close") & post) { channelName =>
    ???
  }

  private val joinChannelRoute = path(Segment / "join") { channelName =>
    (post & entity(as[JoinChannelRequest])) { request =>
      ???
    }
  }

  private val sendMessageToChannelRoute = path(Segment / "send") { channelName =>
    (post & entity(as[SendMessageToChannelRequest])) { request =>
      ???
    }
  }

  private val createChannelRoute = (post & entity(as[ChannelController.CreateChannelRequest])) { request =>
    ???
  }

  private val renderChannelRoute = (path(Segment) & parameters('lastMessagesNumber.as[Int])) { (channelName, lastMessagesNumber) =>
    ???
  }
}
