object PatternMachine {

  def main(args: Array[String]): Unit = {

    /**
     * COMPOSE AND THEN
     */
    composeAndThen()

    /**
     * PARTIAL FUNCTION
     */
    partialFunction()
  }

  def composeAndThen(): Unit = {
    def f(s: String) = s"f($s)"

    def g(s: String) = s"g($s)"

    val fComposeG = f _ compose g _
    println(fComposeG("yay"))

    val fAndThenG = f _ andThen g _
    println(fAndThenG("yay"))
  }

  def partialFunction(): Unit = {
    //requiere como entrada un Int y devuleve un String
    val one: PartialFunction[Int, String] = {
      case 1 => "one"
    }

    //me devuelve si esta definido o no
    println(one.isDefinedAt(1))
    println(one.isDefinedAt(2))

    //me devuelve el valor
    println(one(1))

    val two: PartialFunction[Int, String] = {
      case 2 => "two"
    }

    val three: PartialFunction[Int, String] = {
      case 3 => "three"
    }

    val wildcard: PartialFunction[Int, String] = {
      case _ => "something else"
    }

    val partial = one orElse two orElse three orElse wildcard
    println(partial(5))
    println(partial(3))
    println(partial(2))
    println(partial(1))
    println(partial(0))
  }

}
