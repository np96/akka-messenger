package workshop.common.db.models

import workshop.ChannelUser

/**
  * Created by Nikolay Poperechnyi on 15.05.18.
  */
trait ChannelUserModel extends DatabaseModel with UserModel with ChannelModel {
  import profile.api._
  class ChannelsUsers(tag: Tag) extends Table[ChannelUser](tag, "channel") {
    def channelName = column[String]("c_name")
    def userLogin = column[String]("u_login")


    def pk = primaryKey("pk", (channelName, userLogin))
    def fk_a = foreignKey("fk_a", channelName, channels)
    def fk_b = foreignKey("fk_b", userLogin, users)

    override def * = (channelName, userLogin) <> (ChannelUser.tupled, ChannelUser.unapply)
  }


  val channelsUsers = TableQuery[ChannelsUsers]

}
