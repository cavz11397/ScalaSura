import reactivemongo.api.MongoConnection.ParsedURI

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection, MongoConnectionOptions, WriteConcern}
import reactivemongo.api.bson.{BSONDocumentHandler, BSONDocumentReader, BSONDocumentWriter, Macros, document}
import ExecutionContext.Implicits.global
import reactivemongo.api.bson.collection.BSONCollection

import scala.util.{Failure, Success}
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.commands.{SuccessfulAuthentication, WriteResult}

import scala.util.Try
import reactivemongo.api.bson._

object ReactiveMongo {

  def main(args: Array[String]): Unit = {
    /**
     * GetStarted
     */
    getStarted()

    /**
     * connect database
     */
    connectDatabaseExample()

    /**
     * database and collections
     */
    //    dbFromConnection(mongoConnection)

    /**
     * write documents
     */
    //    simpleInsert()

  }

  /**
   * GetStarted
   */
  def getStarted(): Unit = {
    /**
     * Crear Persona
     */
    createPerson(Person("Alejo", "Zuluaga", 25))
    createPerson(Person("Cristian", "Ochoa", 20))
    createPerson(Person("Andres", "Manrique", 45))
    createPerson(Person("Omar", "Henao", 60))
    createPerson(Person("Alba", "Vasquez", 50))
    createPerson(Person("Nancy", "Usme", 25))
    createPerson(Person("Amparo", "Martinez", 32))

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
  val mongoUri = "mongodb://localhost:27017/prueba"

  // Connect to the database: Must be done only once per application
  val driver: AsyncDriver = AsyncDriver()
  val parsedUri: Future[ParsedURI] = MongoConnection.fromString(mongoUri)

  // Database and collections: Get references
  val futureConnection: Future[MongoConnection] = parsedUri.flatMap(driver.connect(_))

  def db1: Future[DB] = futureConnection.flatMap(_.database("prueba"))

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

  /**
   * CONNECT DATABASE
   */

  /*
  * Conectarse a la base de datos
  * */

  //  Lo primero que necesita es crear una nueva instancia de AsyncDriver.
  val driver1: AsyncDriver = new reactivemongo.api.AsyncDriver

  //  A continuación, puede conectarse a un servidor MongoDB.
  val connection3: Future[MongoConnection] = driver1.connect(List("localhost"))

  /*
  * Opciones de conexion
  * */
  val conOpts = MongoConnectionOptions(/* connection options */)
  val connection4 = driver1.connect(List("localhost"), options = conOpts)

  /*
  * Conexión a un conjunto de réplicas
  * */
  val servers6 = List("server1:27017", "server2:27017", "server3:27017")
  val connection6 = driver1.connect(servers6)

  /*
  * Uso de muchas instancias de conexión
  * */
  val serversReplicaSet1 = List("rs11", "rs12", "rs13")
  val connectionReplicaSet1 = driver1.connect(serversReplicaSet1)

  val serversReplicaSet2 = List("rs21", "rs22", "rs23")
  val connectionReplicaSet2 = driver1.connect(serversReplicaSet2)

  /*
  * Control de la autenticación
  * */

  /**
   * Hay dos maneras de dar a ReactiveMongo sus credenciales.
   * Se puede hacer usando driver.connect.
   */

  def servers7: List[String] = List("server1", "server2")

  val dbName = "somedatabase"
  val userName = "username"
  val password = "password"
  val connection7 = driver1.connect(
    nodes = servers7,
    options = MongoConnectionOptions(
      credentials = Map(dbName -> MongoConnectionOptions.
        Credential(userName, Some(password)))))

  /**
   * El uso de esta función con un URI permite indicar las credenciales de este URI.connection
   * También hay una función authenticate para las referencias de base de datos.
   */

  def authenticateDB(con: MongoConnection): Future[Unit] = {
    def username = "anyUser"

    def password = "correspondingPass"

    val futureAuthenticated: Future[SuccessfulAuthentication] = con.authenticate("mydb", username, password)
    futureAuthenticated.map { _ =>
      // doSomething
    }
  }

  /*
  * Conectarse mediante el URI de MongoDB
  * */

  /**
   * mongodb:[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?[option1=value1][&option2=value2][...&optionN=valueN]]
   * Si las credenciales y el nombre de la base de datos se incluyen en el URI, ReactiveMongo autenticará las conexiones en esa base de datos.
   */
  // connect to the replica set composed of `host1:27018`, `host2:27019` and `host3:27020`
  // and authenticate on the database `somedb` with user `user123` and password `passwd123`
  val uri = "mongodb://user123:passwd123@host1:27018,host2:27019,host3:27020/somedb"

  def connection7(driver: AsyncDriver): Future[MongoConnection] = for {
    parsedUri <- MongoConnection.fromString(uri)
    con <- driver.connect(parsedUri)
  } yield con

  //EJEMPLO
  def connectDatabaseExample(): Unit = {
    val uriEjemplo = "mongodb://localhost:27017/prueba"

    val driverEjemplo = new AsyncDriver

    val database = for {
      uri <- MongoConnection.fromString(uriEjemplo)
      con <- driverEjemplo.connect(uri)
      dn <- Future(uri.db.get)
      db <- con.database(dn)
    } yield db

    database.onComplete {
      case resolution =>
        println(s"DB resolution: $resolution")
        driverEjemplo.close()
    }
  }

  /**
   * DATABASE AND COLLECTIONS
   */
  //  Una vez que tenga una conexión y haya resuelto la base de datos,se puede hacer referencia fácilmente a las colecciones.

  def dbFromConnection(connection: MongoConnection): Future[BSONCollection] =
    connection.database("prueba").
      map(_.collection("person"))

  /**
   * WRITE DOCUMENTS
   */

  /*
  * Insertar un documento
  * */

  //  Las inserciones se realizan con la función insert.

  val document1 = BSONDocument(
    "firstName" -> "Stephane",
    "lastName" -> "Godbillon",
    "age" -> 29)

  // Simple: .insert.one(t)
  def simpleInsert(coll: BSONCollection): Future[Unit] = {
    val writeRes: Future[WriteResult] = coll.insert.one(document1)

    writeRes.onComplete { // Dummy callbacks
      case Failure(e) => e.printStackTrace()
      case Success(writeResult) =>
        println(s"successfully inserted document with result: $writeResult")
    }

    writeRes.map(_ => {}) // in this example, do nothing with the success
  }

  // Bulk: .insert.many(Seq(t1, t2, ..., tN))
  def bulkInsert(coll: BSONCollection): Future[Unit] = {
    val writeRes: Future[coll.MultiBulkWriteResult] =
      coll.insert(ordered = false).many(Seq(
        document1, BSONDocument(
          "firstName" -> "Foo",
          "lastName" -> "Bar",
          "age" -> 1)))

    writeRes.onComplete { // Dummy callbacks
      case Failure(e) => e.printStackTrace()
      case Success(writeResult) =>
        println(s"successfully inserted document with result: $writeResult")
    }

    writeRes.map(_ => {}) // in this example, do nothing with the success
  }

  //Example
  val person = Person("Stephane", "Godbillon", 29)

  def testInsert(personColl: BSONCollection) = {
    val future2 = personColl.insert.one(person)
    future2.onComplete {
      case Failure(e) => throw e
      case Success(writeResult) => {
        println(s"successfully inserted document: $writeResult")
      }
    }
  }

  /*
   * control de errores
   */
  def insertErrors(personColl: BSONCollection) = {
    val future: Future[WriteResult] = personColl.insert.one(person)

    val end: Future[Unit] = future.map(_ => {}).recover {
      case WriteResult.Code(11000) =>
        // if the result is defined with the error code 11000 (duplicate error)
        println("Match the code 11000")

      case WriteResult.Message("Must match this exact message") =>
        println("Match the error message")

      case WriteResult.Exception(cause) =>
        cause.printStackTrace() // Print any other Exception

      case _ => ()
    }
  }

  /*
   * Actualizar un documento
   */

  def update1(personColl: BSONCollection) = {
    val selector = BSONDocument("name" -> "Jack")

    val modifier = BSONDocument(
      "$set" -> BSONDocument(
        "lastName" -> "London",
        "firstName" -> "Jack"),
      "$unset" -> BSONDocument("name" -> 1))

    // Simple update: get a future update
    val futureUpdate1 = personColl.update.one(
      q = selector, u = modifier,
      upsert = false, multi = false)

    // Bulk update: multiple update
    val updateBuilder1 = personColl.update(ordered = true)
    val updates = Future.sequence(Seq(
      updateBuilder1.element(
        q = BSONDocument("firstName" -> "Jane", "lastName" -> "Doh"),
        u = BSONDocument("age" -> 18),
        upsert = true,
        multi = false),
      updateBuilder1.element(
        q = BSONDocument("firstName" -> "Bob"),
        u = BSONDocument("age" -> 19),
        upsert = false,
        multi = true)))

    val bulkUpdateRes1 = updates.flatMap { ops => updateBuilder1.many(ops) }
  }

  def updateArrayFilters(personColl: BSONCollection)(
    implicit ec: ExecutionContext) =
    personColl.update.one(
      q = BSONDocument("grades" -> BSONDocument(f"$$gte" -> 100)),
      u = BSONDocument(f"$$set" -> BSONDocument(
        f"grades.$$[element]" -> 100)),
      upsert = false,
      multi = true,
      collation = None,
      arrayFilters = Seq(
        BSONDocument("element" -> BSONDocument(f"$$gte" -> 100))))

  /*
   * Eliminar un documento
   */

  def simpleDelete1(personColl: BSONCollection) = {
    val selector1 = BSONDocument("firstName" -> "Stephane")

    val futureRemove1 = personColl.delete.one(selector1)

    futureRemove1.onComplete { // callback
      case Failure(e) => throw e
      case Success(writeResult) => println("successfully removed document")
    }
  }

  def bulkDelete1(personColl: BSONCollection) = {
    val deleteBuilder = personColl.delete(ordered = false)

    val deletes = Future.sequence(Seq(
      deleteBuilder.element(
        q = BSONDocument("firstName" -> "Stephane"),
        limit = Some(1), // former option firstMatch
        collation = None),
      deleteBuilder.element(
        q = BSONDocument("lastName" -> "Doh"),
        limit = None, // delete all the matching document
        collation = None)))

    deletes.flatMap { ops => deleteBuilder.many(ops) }
  }

  /*
   * Buscar y modificar
   */
  case class Person2(name: String, age: Int)

  def update(collection: BSONCollection, age: Int): Future[Option[Person2]] = {
    implicit val reader = Macros.reader[Person2]

    val result = collection.findAndUpdate(
      BSONDocument("name" -> "James"),
      BSONDocument("$set" -> BSONDocument("age" -> 17)),
      fetchNewObject = true)

    result.map(_.result[Person2])
  }

  //  Al igual que en una simple actualización, es posible insertar un nuevo documento cuando aún no existe uno.
  implicit val handler: BSONDocumentHandler[Person2] = Macros.handler[Person2]

  /** Insert a new document if a matching one does not already exist. */
  def result(coll: BSONCollection): Future[Option[Person2]] =
    coll.findAndUpdate(
      BSONDocument("name" -> "James"),
      Person2(name = "Foo", age = 25),
      upsert = true).map(_.result[Person2])

  //  El enfoque findAndModify se puede usar en la eliminación.

  def removedPerson(coll: BSONCollection, name: String)(implicit ec: ExecutionContext, reader: BSONDocumentReader[Person2]): Future[Option[Person2]] =
    coll.findAndRemove(BSONDocument("name" -> name)).
      map(_.result[Person2])

  //  Al igual que cuando se utiliza update arrayFilters, se pueden especificar criterios para una operación.findAndModify

  def findAndUpdateArrayFilters(personColl: BSONCollection) =
    personColl.findAndModify(
      selector = BSONDocument.empty,
      modifier = personColl.updateModifier(
        update = BSONDocument(f"$$set" -> BSONDocument(
          f"grades.$$[element]" -> 100)),
        fetchNewObject = true,
        upsert = false),
      sort = None,
      fields = None,
      bypassDocumentValidation = false,
      writeConcern = WriteConcern.Journaled,
      maxTime = None,
      collation = None,
      arrayFilters = Seq(
        BSONDocument("elem.grade" -> BSONDocument(f"$$gte" -> 85))))

  /*
  * Sesion transaccion
  * */

  def testTx(db: DB)(implicit ec: ExecutionContext): Future[Unit] =
    for {
      dbWithSession <- db.startSession()
      dbWithTx <- dbWithSession.startTransaction(None)
      coll = dbWithTx.collection("foo")

      _ <- coll.insert.one(BSONDocument("id" -> 1, "bar" -> "lorem"))
      r <- coll.find(BSONDocument("id" -> 1)).one[BSONDocument] // found

      _ <- db.collection("foo").find(
        BSONDocument("id" -> 1)).one[BSONDocument]
      // not found for DB outside transaction

      _ <- dbWithTx.commitTransaction() // or abortTransaction()
      // session still open, can start another transaction, or other ops

      _ <- dbWithSession.endSession()
    } yield ()

  /**
   * BUSCAR DOCUMENTOS
   */

  /*
  * Realizar una consulta simple
  * */
  def findOlder1(collection: BSONCollection): Future[Option[BSONDocument]] = {
    // { "age": { "$gt": 27 } }
    val query = BSONDocument("age" -> BSONDocument("$gt" -> 27))

    // MongoDB .findOne
    collection.find(query).one[BSONDocument]
  }

  /*def findOlder2(collection: BSONCollection) = {
    val query = BSONDocument("age" -> BSONDocument("$gt" -> 27))

    // only fetch the name field for the result documents
    val projection = BSONDocument("name" -> 1)

    collection.find(query, projection).cursor[BSONDocument]().
      collect[List](25, // get up to 25 documents
        Cursor.FailOnError[List[BSONDocument]]())
  }*/

  /** *
   * El paquete no encuentra el QueryOpts
   */
  /*def findNOlder(collection: BSONCollection, limit: Int) = {
    val querybuilder =
      collection.find(BSONDocument("age" -> BSONDocument("$gt" -> 27)))

    // Sets options before executing the query
    querybuilder.options(QueryOpts().batchSize(limit)).
      cursor[BSONDocument]().collect[List](10, // get up to 10 documents
      Cursor.FailOnError[List[BSONDocument]]())

  }*/

  /** *
   * BSON DOCUMENT
   */


  val bsonDouble = BSONDouble(12.34D)
  println(bsonDouble.value)

  val bsonStr = BSONString("foo")
  println(bsonStr.value)

  val bsonInt = BSONInteger(2345)
  println(bsonInt.value)

  // BSON array: [ 12.34, "foo", 2345 ]
  val bsonArray = BSONArray(bsonDouble, bsonStr, bsonInt)

  val bsonEmptyDoc: BSONDocument = BSONDocument.empty
  println(bsonEmptyDoc.values)
  /* BSON Document:
  {
    'foo': 'bar',
    'lorem': 2,
    'values': [ 12.34, "foo", 2345 ],
    'nested': {
      'position': 1000,
      'score': 1.2
    }
  }
  */
  val bsonDoc = BSONDocument(
  "foo" -> "bar", // as BSONString
  "lorem" -> 2, // as BSONInteger
  "values" -> bsonArray,
  "nested" -> BSONDocument(
  "position" -> 1000,
  "score" -> 1.2D // as BSONDouble
  )
  )
  bsonDoc.values.map(x=>println(x))

  val bsonBin = BSONBinary(Array[Byte](0, 1, 2), Subtype.GenericBinarySubtype)
  // See Subtype

  val bsonObjectID = BSONObjectID.generate()

  val bsonBool = BSONBoolean(true)

  val bsonDateTime = BSONDateTime(System.currentTimeMillis())

  val bsonRegex = BSONRegex("/foo/bar/", "g")

  val bsonJavaScript = BSONJavaScript("lorem(0)")

  val bsonJavaScriptWs = BSONJavaScriptWS("bar", BSONDocument("bar" -> 1))

  val bsonTimestamp = BSONTimestamp(45678L)

  val bsonLong = BSONLong(Long.MaxValue)

  val bsonZeroDecimal = BSONDecimal.PositiveZero

  val bsonDecimal: Try[BSONDecimal] =
    BSONDecimal.fromBigDecimal(BigDecimal("12.23"))

  val bsonNull = BSONNull

  val bsonMinKey = BSONMinKey

  val bsonMaxKey = BSONMaxKey

}

