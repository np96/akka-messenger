package workshop.modules

import slick.jdbc.{JdbcBackend, PostgresProfile}

trait DatabaseModule {
  lazy val database = JdbcBackend.Database.forConfig("db")
  lazy val databaseProfile = PostgresProfile
}
