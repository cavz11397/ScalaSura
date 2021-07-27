import scala.concurrent.{
  ExecutionContext, Future
}
import reactivemongo.api.{
  Cursor, DB, MongoConnection, AsyncDriver
}
import reactivemongo.api.bson.{
  BSONDocumentWriter, BSONDocumentReader, Macros, document
}
import ExecutionContext.Implicits.global // use any appropriate context

object GetStarted {

  def main(args: Array[String]): Unit = {
    /**
     * Crear Persona
     */
    /*createPerson(Person("Alejo", "Zuluaga", 25))
    createPerson(Person("Cristian", "Ochoa", 20))
    createPerson(Person("Andres", "Manrique", 45))
    createPerson(Person("Omar", "Henao", 60))
    createPerson(Person("Alba", "Vasquez", 50))
    createPerson(Person("Nancy", "Usme", 25))
    createPerson(Person("Amparo", "Martinez", 32))
*/
    /**
     * Actualizar Persona
     */
//        updatePerson(Person("Alejo", "Zuluaga", 60))

    /**
     * Buscar Personas por edad
     */
//        findPersonByAge(25).map(x=>println(x))
  }

  // My settings (see available connection options)
  val mongoUri = "mongodb://localhost:27017/spring_boot"

  // Connect to the database: Must be done only once per application
  val driver = AsyncDriver()
  val parsedUri = MongoConnection.fromString(mongoUri)

  // Database and collections: Get references
  val futureConnection = parsedUri.flatMap(driver.connect(_))

  def db1: Future[DB] = futureConnection.flatMap(_.database("spring_boot"))

  //    def db2: Future[DB] = futureConnection.flatMap(_.database("anotherdb"))
  def personCollection = db1.map(_.collection("person"))

  // Write Documents: insert or update
  implicit def personWriter: BSONDocumentWriter[Person] = Macros.handler[Person]
  // or provide a custom one

  // use personWriter
  def createPerson(person: Person): Future[Unit] =
    personCollection.flatMap(_.insert.one(person).map(_ => {}))

  def updatePerson(person: Person): Future[Int] = {
    val selector = document(
      "firstName" -> person.firstName,
      "lastName" -> person.lastName
    )

    // Update the matching person
    personCollection.flatMap(_.update.one(selector, person).map(_.n))
  }

  implicit def personReader: BSONDocumentReader[Person] = Macros.reader[Person]
  // or provide a custom one

  def findPersonByAge(age: Int): Future[List[Person]] =
    personCollection.flatMap(_.find(document("age" -> age)). // query builder
      cursor[Person](). // using the result cursor
      collect[List](-1, Cursor.FailOnError[List[Person]]()))
  // ... deserializes the document using personReader

  // Custom persistent types
  case class Person(firstName: String, lastName: String, age: Int)
}

