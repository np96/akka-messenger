import java.time.Instant

package object workshop {
  case class Channel(name: String, description: Option[String], archived: Boolean)
  case class User(login: String, firstName: Option[String], lastName: Option[String], status: Option[String])
  case class ChannelUser(channelName: String, userLogin: String)
  case class Message(sender: User, text: String, createAt: Instant)
}
