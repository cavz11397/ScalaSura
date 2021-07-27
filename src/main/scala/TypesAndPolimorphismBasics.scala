object TypesAndPolimorphismBasics {

  def main(args: Array[String]): Unit = {
    /**
     * COVARIANTE Y CONTRAVARIANTE
     */
    covarianteYContravariante()
    clases()

    /**
     * LIMITES
     */
    limites()
  }

  class Animal {
    val sound = "rustle"
  }

  class Bird extends Animal {
    override val sound = "call"
  }

  class Chicken extends Bird {
    override val sound = "cluck"
  }

  def covarianteYContravariante(): Unit = {
    def id[T](x: T) = x

    val x = id(322)
    println(x)

    val y = id("hey")
    println(y)

    val z = id(Array(1, 2, 3, 4))
    z.map(x => print(x + ", "))
    println()

  }

  def clases(): Unit = {
    val getTweet: (Bird => String) = ((a: Animal) => a.sound)
    println(getTweet.getClass)

    val hatch: (() => Bird) = (() => new Chicken)
    println(hatch.getClass)
  }

  def limites(): Unit = {
    def biophony[T <: Animal](things: Seq[T]) = things map (_.sound)

    biophony(Seq(new Chicken, new Bird)).map(x => println(x))
    val flock = List(new Bird, new Bird)

    // Agrega el nuevo chicken a la flock
    val pollito = new Chicken :: flock
    val animal = new Animal :: flock

    flock.map(x => println(x.sound))
    pollito.map(x => println(x.sound))
    animal.map(x => println(x.sound))
  }

}
