import scala.concurrent.Future
import akka.actor.ActorSystem
import spray.caching.{Cache, LruCache}
import spray.util._

import scala.concurrent.duration.Duration

object CacheSpray {

  def main(args: Array[String]): Unit = {}

  val system = ActorSystem()

  import system.dispatcher

  // if we have an "expensive" operation
  def expensiveOp(): Double = new util.Random().nextDouble()

  // and a Cache for its result type
  val cache: Cache[Double] = LruCache()

  // we can wrap the operation with caching support
  // (providing a caching key)
  def cachedOp[T](key: T): Future[Double] = cache(key) {
    expensiveOp()
  }

  // and profit
  cachedOp("foo").await == cachedOp("foo").await
  cachedOp("bar").await != cachedOp("foo").await

  /**
   * Creates a new [[spray.caching.ExpiringLruCache]] or
   * [[spray.caching.SimpleLruCache]] instance depending on whether
   * a non-zero and finite timeToLive and/or timeToIdle is set or not.
   */
  /*def apply[V](maxCapacity: Int = 500,
               initialCapacity: Int = 16,
               timeToLive: Duration = Duration.Inf,
               timeToIdle: Duration = Duration.Inf): Cache[V] = {}*/
}
