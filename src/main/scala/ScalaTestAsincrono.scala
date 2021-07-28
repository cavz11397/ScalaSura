import org.scalatest.flatspec.AsyncFlatSpec
import java.io.File
import org.scalatest._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

class ScalaTestAsincrono {}

class AddSpec extends AsyncFlatSpec {

  def addSoon(addends: Int*): Future[Int] = Future {
    addends.sum
  }

  behavior of "addSoon"

  it should "eventually compute a sum of passed Ints" in {
    val futureSum: Future[Int] = addSoon(1, 2)
    // You can map assertions onto a Future, then return
    // the resulting Future[Assertion] to ScalaTest:
    futureSum map { sum => assert(sum == 3) }
  }

  def addNow(addends: Int*): Int = addends.sum

  /*"addNow" should "immediately compute a sum of passed Ints" in {
    val sum: Int = addNow(1, 2)
    // You can also write synchronous tests. The body
    // must have result type Assertion:
    assert(sum == 3)
  }*/

  "addNow" should "immediately compute a sum of passed Ints" in {
    val sum: Int = addNow(1, 2)
    assert(sum == 3)
    println("hi") // println has result type Unit
    succeed // succeed has result type Assertion
  }

}

class ExampleSpec2 extends AsyncFlatSpec {

  override def withFixture(test: NoArgAsyncTest) = {

    super.withFixture(test) onFailedThen { _ =>
      val currDir = new File(".")
      val fileNames = currDir.list()
      info("Dir snapshot: " + fileNames.mkString(", "))
    }
  }

  def addSoon(addends: Int*): Future[Int] = Future {
    addends.sum
  }

  "This test" should "succeed" in {
    addSoon(1, 1) map { sum => assert(sum == 2) }
  }

  it should "fail" in {
    addSoon(1, 1) map { sum => assert(sum == 3) }
  }
}


// Defining actor messages
sealed abstract class StringOp
case object Clear extends StringOp
case class Append(value: String) extends StringOp
case object GetValue

class StringActor { // Simulating an actor
  private final val sb = new StringBuilder
  def !(op: StringOp): Unit =
    synchronized {
      op match {
        case Append(value) => sb.append(value)
        case Clear => sb.clear()
      }
    }
  def ?(get: GetValue.type)(implicit c: ExecutionContext): Future[String] =
    Future {
      synchronized { sb.toString }
    }
}

/*
class ExampleSpec extends AsyncFlatSpec {

  type FixtureParam = StringActor

  def withFixture(test: OneArgAsyncTest): FutureOutcome = {

    val actor = new StringActor
    complete {
      actor ! Append("ScalaTest is ") // set up the fixture
      withFixture(test.toNoArgAsyncTest(actor))
    } lastly {
      actor ! Clear // ensure the fixture will be cleaned up
    }
  }

  "Testing" should "be easy" in { actor =>
    actor ! Append("easy!")
    val futureString = actor ? GetValue
    futureString map { s =>
      assert(s == "ScalaTest is easy!")
    }
  }

  it should "be fun" in { actor =>
    actor ! Append("fun!")
    val futureString = actor ? GetValue
    futureString map { s =>
      assert(s == "ScalaTest is fun!")
    }
  }
}*/
