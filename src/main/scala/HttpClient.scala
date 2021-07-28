import akka.actor.ActorSystem
import scala.concurrent.Future
import akka.io.IO
import akka.pattern.ask
import spray.can.Http
import spray.http._
import spray.client.pipelining._

object HttpClient {

  /*def main(args: Array[String]): Unit = {

  }

  implicit val system = ActorSystem()

  import system.dispatcher // execution context for futures

//  val pipeline: HttpRequest => Future[HttpResponse] = sendReceive

//  val response: Future[HttpResponse] = pipeline(Get("http://spray.io/"))

  val pipeline: Future[SendReceive] =
    for (
      Http.HostConnectorInfo(connector, _) <-
        IO(Http) ? Http.HostConnectorSetup("www.spray.io", port = 80)
    ) yield sendReceive(connector)

  val request = Get("/")
  val response: Future[HttpResponse] = pipeline.flatMap(_(request))*/
}
