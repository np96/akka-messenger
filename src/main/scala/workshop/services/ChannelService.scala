package workshop.services

import slick.jdbc.JdbcProfile
import slick.jdbc.JdbcBackend
import workshop.common.concurrent.ApplicationContext
import workshop.common.db.models.{ChannelModel, ChannelUserModel}
import workshop.{Channel, ChannelUser}

import scala.concurrent.Future

/**
  * Created by Nikolay Poperechnyi on 15.05.18.
  */
trait ChannelService {
  def channelInfo(channelName: String): Future[Option[Channel]]

  def createChannel(name: String, description: String): Future[Channel]

  def joinChannel(userName: String, channelName: String): Future[Boolean]

  def sendMessageToChannel(
    channelName: String,
    userName: String,
    text: String
  ): Future[Boolean]

}

class ChannelServiceImpl(val profile: JdbcProfile, db: JdbcBackend.Database)
  extends ChannelService with ApplicationContext with ChannelModel
    with ChannelUserModel {

  import profile.api._

  override def channelInfo(
    channelName: String
  ): Future[Option[Channel]] = {
    db.run {
      channels.filter(_.name === channelName).result.headOption
    }
  }

  override def createChannel(
    name: String, description: String
  ): Future[Channel] = {
    val c = Channel(name, Some(description), archived = false)
    db.run {
      channels += c
    }.map(_ => c)
  }

  override def joinChannel(
    userName: String, channelName: String
  ): Future[Boolean] = {
    val cu = ChannelUser(userName, channelName)
    db.run {
      channelsUsers += cu
    }.map(_ == 1)
  }

  override def sendMessageToChannel(
    channelName: String, userName: String, text: String
  ): Future[Boolean] = ???
}
