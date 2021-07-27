import scala.collection.{immutable, mutable}

object Collecciones {

  /**
   * COLLECCIONES
   *
   * ANY
   * AnyVal en scala: Int, Double, Float, Long, Byte, Short, Char, Boolean, Unit
   * AnyRef en scala: por ej un ArrayList importadp de java
   * Null
   * Nothing Es todos los tipos a la vez
   * -------------------------------------------------------------------------------------
   * En las collecciones pasa lo mismo que en la declaracion de variables
   * INMUTABLES, se refiere a que cuando cree los elementos ya no pueda cambiarlos
   * MUTABLES
   */

  def main(args: Array[String]): Unit = {

    /**
     * COLECCIONES
     */
    metodosBasicosDeUnArray()
    metodosBasicosDeUnaList()

    /**
     * COLECCIONES ANIDADAS
     */
    metodoFlattenYFlatmap()
    metodoDistinct()

    /**
     * SETS
     */
    coleccionesSetInmutables()
    coleccionesSetMutables()
    ordenacionDeSets()

    /**
     * MAPAS
     */
    mapasBasicos()

    /**
     * CONVERSIONES
     */

  }

  def metodosBasicosDeUnArray(): Unit = {
    val frutas = Array("pera", "manzana", "fresa", "banano")

    //sirve para recibir el objeto en esa posicion, las 2 sig lineas funcionan igual
    println(frutas.apply(1))
    println(frutas(1))
    println(frutas.length)
    println(frutas.isEmpty)
    println(frutas.nonEmpty)
    println(frutas.indexOf("pera"))
  }

  def metodosBasicosDeUnaList(): Unit = {
    val numero = List(4, 8, 16, 32, 64)

    //quiero otra lista que sea el doble de los numeros
    val numeroDoble = numero.map { x => x * 2 }
    println(numero)
    println(numeroDoble)

    val coches = List("bmw", "seat", "renault", "ferrari")
    /**
     * El _ representa una arrow function en este caso
     * por eso tenemos dos representaciones distintas en la variable cochesUp(nos quedamos con la segunda)
     * val cochesUp = coches.map(x => x.toUpperCase())
     */
    val cochesUp = coches.map(_.toUpperCase())
    println(coches)
    println(cochesUp)
  }

  def metodoFlattenYFlatmap(): Unit = {
    //flatten
    val lista = List(List(1, 2, 6), List(2, 3, 5), List(5, 7, 1), List(4, 8, 3))
    println(lista.flatten)

    //map
    val listaMap = lista.map { (x: List[Int]) => x.map { num => num * 2 } }.flatten
    println(listaMap)

    //flatmap
    val listaFlatMap = lista.flatMap { (x: List[Int]) => x.map { num => num * 2 } }
    println(listaFlatMap)

    val numeros = List(4, 8, 16, 32, 64)
    val numerosLista = numeros.flatMap(x => List(x, x * 2))
    println(numerosLista)

  }

  def metodoDistinct(): Unit = {
    val coches = List("bmw", "seat", "renault", "ferrari")
    println(coches.flatMap { c => c.toUpperCase() })
    println(coches.flatMap { c => c.toUpperCase() }.distinct)
  }

  def coleccionesSetInmutables(): Unit = {
    //SETS: no contienen elementos duplicados
    val set = immutable.Set(1, 2, 3, 4)
    //en el caso de los set el apply llama a contains
    println(set.apply(3))
    println(set.apply(7))

    /**
     * Los siguientes dos metodos crean una nueva coleccion set pero no modifican la original porque esta es inmutable
     */
    println(set ++ Set(5, 6, 7))
    println(set - 4)
    println(set)

    //set utiliza la teoria de conjuntos
    set intersect Set(3, 4)
    set union Set(3, 4)
    set diff Set(3, 4)
  }

  def coleccionesSetMutables(): Unit = {
    val mset = mutable.Set(1, 2, 3)
    println(mset)

    //Lo inserta y modifica
    mset += 4
    println(mset)

    //retain retiene los que no cumplen con cierta condicion, lo modifica, mientras que el filter devuelve otra copia
    mset retain { x => x % 2 == 0 }
    println(mset)
  }

  def ordenacionDeSets(): Unit = {
    val set = mutable.Set(1, 2, 3, 4, 5, 6)
    println(set)
    val iset = immutable.SortedSet(1, 2, 3, 4, 5, 6)
    println(iset)

    //Orden inverso
    val mayorAMenor = Ordering.fromLessThan[Int](_ > _)
    val sortedSetInverso = immutable.SortedSet.empty(mayorAMenor) ++ iset
    println(sortedSetInverso)
  }

  def mapasBasicos(): Unit = {
    /**
     * INMUTABLES
     * */
    //Claves <- A , Valores <- B, (CLAVE -> VALOR)
    val mapa = Map(1 -> "luis", 2 -> "helena", 3 -> "joaquin")
    println(mapa.keySet)
    println(mapa.values)

    //en un option puede haber algo o no
    println(mapa.get(2))
    println(mapa(2))

    //Este mapa es inmutable, por lo tanto no se modificara sino que creara otro
    println(mapa + (4 -> "Alejo"))
    println(mapa.values)

    val mapa1 = Map(1 -> "TOYOTA", 2 -> "BMW")
    val mapa2 = Map(2 -> "RENAULT", 3 -> "FERRARI")
    println(mapa1 ++ mapa2)

    /**
     * MUTABLES
     * */
    val mmapa = mutable.Map(1 -> "TOYOTA", 2 -> "BMW")
    mmapa += (4 -> "RENAULT", 3 -> "FERRARI")
    println(mmapa)
    mmapa.put(5, "LAMBORGINI")
    println(mmapa)
  }

  /*def conversionesBidireccionales(): Unit ={
    val sl = new scala.collection.mutable.ListBuffer[Int]
    val jl : java.util.List[Int] = sl.asJava
    val sl2 : scala.collection.mutable.Buffer[Int] = jl.asScala
    assert(sl eq sl2)

    scala.collection.Iterable <=> java.lang.Iterable
    scala.collection.Iterable <=> java.util.Collection
    scala.collection.Iterator <=> util { Iterator, util.Enumeration }
    scala.collection.mutable.Buffer <=> java.util.List
    scala.collection.mutable.Set <=> java.util.Set
    scala.collection.mutable.Map <=> java.util.{ Map, Dictionary }
    scala.collection.mutable.ConcurrentMap <=> java.util.concurrent.ConcurrentMap

  }

    def conversioneUnidireccionales(): Unit ={
    scala.collection.Seq => java.util.List
    scala.collection.mutable.Seq => java.util.List
    scala.collection.Set => java.util.Set
    scala.collection.Map => java.util.Map

  }*/

}
