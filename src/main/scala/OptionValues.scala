import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class OptionValues extends AnyFlatSpec{

  val opt: Option[Int] = Some(10)

  opt.get should be > 9
}
