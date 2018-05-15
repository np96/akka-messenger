package workshop.common.db.models

import slick.jdbc.JdbcProfile

trait DatabaseModel {
  val profile: JdbcProfile
}
