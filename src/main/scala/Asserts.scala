import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.Assertions.assertThrows

class Asserts extends AnyFlatSpec {

  /**
   * Assserts normales
   * */

  /*val left = 2
  val right = 1
  assert(left == right)*/

  /*assert(a == b || c >= d)
  // Error message: 1 did not equal 2, and 3 was not greater than or equal to 4

  assert(xs.exists(_ == 4))
  // Error message: List(1, 2, 3) did not contain 4

  assert("hello".startsWith("h") && "goodbye".endsWith("y"))
  // Error message: "hello" started with "h", but "goodbye" did not end with "y"

  assert(num.isInstanceOf[Int])
  // Error message: 1.0 was not instance of scala.Int*/

  /* assert(Some(2).isEmpty)
   // Error message: Some(2) was not empty*/

  /*assert(None.isDefined)
  // Error message: scala.None.isDefined was false*/

  /*assert(xs.exists(i => i > 10))
  // Error message: xs.exists(((i: Int) => i.>(10))) was false*/

  /*val attempted = 2
  assert(attempted == 1, "Execution was attempted " + left + " times instead of 1 time")*/

  /**
   * asserResults
   */

  /*val a = 5
  val b = 2
  assertResult(3) {
    a - b
  }*/

  /**
   * fail
   * */

  //  fail("I've got a bad feeling about this.")

  /**
   * excepciones esperadas
   * */
  /*val s = "hi"
  try {
    s.charAt(-1)
    fail()
  }
  catch {
    case _: IndexOutOfBoundsException => // Expected, so continue
  }*/

  /*val s = "hi"
  assertThrows[IndexOutOfBoundsException] { // Result type: Assertion
    s.charAt(-1)
  }*/

  /*val s = "hi"
  val caught = {
    intercept[IndexOutOfBoundsException] { // Result type: IndexOutOfBoundsException
      s.charAt(-1)
    }
  }
//  println(s.charAt(-1))
  assert(caught.getMessage.indexOf("-1") != -1)*/

//  assertResult(3, "this is a clue") { 1 + 1 }
  withClue("this is a clue") {
    assertThrows[IndexOutOfBoundsException] {
      "hi".charAt(-1)
    }
  }
}
