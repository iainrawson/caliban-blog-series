package client

import client.TrainClient.{Query, Searchable, Station}
import caliban.client.Operations.RootQuery
import caliban.client.SelectionBuilder
import cats.effect.{ExitCode, IO, IOApp, Resource}
import sttp.capabilities.fs2.Fs2Streams
import sttp.client3.*
import sttp.client3.http4s._
import cats.syntax.functor.*

object TrainApp extends IOApp {

  case class Train(`type`: String, platform: String, trainNumber: String, time: String, stops: List[String])

  def run(args: List[String]): IO[ExitCode] = {

    val query =
      Query.search(Some("Berlin")) {
        Searchable.stations {
          Station.name ~
            Station.hasWiFi
        }
      }

    val uri = uri"https://api.deutschebahn.com/free1bahnql/v1/graphql"

    Http4sBackend.usingDefaultBlazeClientBuilder[IO]().use { sttpBackend =>
      sttpBackend.send(query.toRequest(uri))
    }.map { response =>
      response.body.map( _.filter(_._2) )
    }.flatMap { result =>
      IO.delay {
        println(result.fold(_.toString, _.toString))
      }
    }.as(ExitCode.Success)

  }

}