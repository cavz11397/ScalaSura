import scala.annotation.tailrec

object TailRecursion {

  def main(args: Array[String]): Unit = {
    println(factorial(5))
    println(tailRecFactorial(5))
  }

  def  factorial(n: Int): Int={
    if   (n  <=  1)   1
    else  n  *   factorial(n-  1)
  }


  def   tailRecFactorial(n: Int):BigInt ={
    @tailrec
    def   factorialHelper(x: Int, acumulador: BigInt):BigInt ={
      if   (x  <=  1)  acumulador
      else   factorialHelper(x-  1,x  *  acumulador)
    }
    factorialHelper(n,  1)
  }
}
