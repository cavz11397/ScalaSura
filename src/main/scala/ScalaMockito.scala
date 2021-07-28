import org.scalatest.flatspec.AnyFlatSpec
import org.scalamock.scalatest.MockFactory
import org.scalamock.proxy.ProxyMockFactory

class ScalaMockito {

}

class ExampleSpec extends AnyFlatSpec with MockFactory {
  /*val m = mockFunction[Int, String]

  m expects (42) returning "Forty two" once

  val m = mock[Turtle]

  m expects 'setPosition withArgs(10.0, 10.0)
  m expects 'forward withArgs (5.0)
  m expects 'getPosition returning(15.0, 10.0)

  m expects 'forward withArgs (*) once
    m expects 'forward

  m expects 'forward anyNumberOfTimes
    m stubs 'forward*/
}

/*
class ecample2 extends AnyFlatSpec with MockFactory with ProxyMockFactory {
  val m = mock[Turtle]

  m.expects.forward(10.0) twice
}*/
