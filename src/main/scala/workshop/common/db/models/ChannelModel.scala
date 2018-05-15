package workshop.common.db.models

import workshop.Channel

trait ChannelModel extends DatabaseModel {
  import profile.api._

  class Channels(tag: Tag) extends Table[Channel](tag, "channel") {
    def name = column[String]("name", O.PrimaryKey)
    def description = column[Option[String]]("description")
    def archived = column[Boolean]("archived")

    override def * = (name, description, archived) <> (Channel.tupled, Channel.unapply)
  }

  val channels = TableQuery[Channels]
}