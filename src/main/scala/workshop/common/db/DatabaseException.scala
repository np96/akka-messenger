package workshop.common.db

sealed abstract class DatabaseException(message: String, cause: Option[Throwable] = None) extends Exception(message, cause.orNull)
class EntityAlreadyExistsException(message: String) extends Exception(message)
class EntityIsNotExistsException(message: String) extends Exception(message)