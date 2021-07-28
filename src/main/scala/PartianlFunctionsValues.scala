import org.scalatest.PartialFunctionValues.convertPartialFunctionToValuable
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.{convertToAnyShouldWrapper, equal}

class PartianlFunctionsValues extends AnyFlatSpec{

  /*val pf: PartialFunction[String, Int] = Map("I" -> 1, "II" -> 2, "III" -> 3, "IV" -> 4)

  pf("I") should equal (1)
  pf("II") should equal (2)
  pf("III") should equal (3)
  pf("IV") should equal (4)
  pf("V") should equal (5)*/

  /*val pf: PartialFunction[String, Int] = Map("I" -> 1, "II" -> 2, "III" -> 3, "IV" -> 4)

  pf.isDefinedAt("V") should be (true) // throws TestFailedException
  pf("V") should equal (5)*/

  val pf: PartialFunction[String, Int] = Map("I" -> 1, "II" -> 2, "III" -> 3, "IV" -> 4)
  assert(pf.valueAt("IV") === 4)
//  pf.valueAt("V") should equal (5)

}
