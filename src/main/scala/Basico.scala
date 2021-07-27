import scala.collection.mutable

object Basico {

  def main(args: Array[String]): Unit = {
    /**
     * DEFINICION DE VARIABLES
     * val es para variables inmutables
     * var es para variables mutables
     */

    /**
     * HELLO WORLD
     */
    helloWorld()

    /**
     * CONDICIONALES
     */
    condicioinalIf()
    condicioinalMatch()

    /**
     * CICLOS
     */
    cicloWhileYDoWhile()
    cicloForYForEach()
  }

  def helloWorld(): Unit = {
    println("Hola mundo")
  }

  def condicioinalIf(): Unit = {
    val n = 5

    if (n == 5) {
      println("n es igual a 5")
    }
  }

  def condicioinalMatch(): Unit = {
    //MATCH : algo parecido al switch
    val mes = 6
    //Se devuelven expresiones, podria poner  println antes del mes
    println(mes match {
      case 1 => "Enero"
      case 2 => "Febrero"
      case 3 => "Marzo"
      case 4 => "Abril"
      case 5 => "Mayo"
      case 6 => "Junio"
      case 7 => "Julio"
      case 8 => "Agosto"
      case 9 => "Septiembre"
      case 10 => "Octubre"
      case 11 => "Noviembre"
      case 12 => "Diciembre"
      //Este ultimo es como si fuera el default
      case _ => "Ninguno"
    })
  }

  def cicloWhileYDoWhile(): Unit = {
    var k = 0
    while (k < 20) {
      //en scala hay cadenas interpoladas, basta con ponerle una s al inicio y la variable añadirle el dolar antes
      println(s"k vale: $k")
      k += 1
    }

    var j = 0: Int
    do {
      println(s"j vale: $j")
      j += 1
    } while (j < 20)

  }

  def cicloForYForEach(): Unit = {
    val lenguajes = mutable.Seq("Java", "JS", "React", "Scala", "Python", "Ruby")
    val opiniones = mutable.Seq("Consiso", "Moderno", "Atrevido", "Anticuado", "Aburrido")

    lenguajes.foreach(l => println(s"$l me parece un buen lenguaje"))

    for (i <- lenguajes if i.endsWith("a");
         o <- opiniones if o.startsWith("A")) {
      println(s"$i me parece un lenguaje $o")
    }
  }

}
