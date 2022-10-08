package org.teckhooi.echo

import cats.effect.std.Semaphore
import cats.effect.{Async, Concurrent, Resource}
import cats.{MonadError, Parallel}
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.headers.`User-Agent`
import org.http4s.implicits.*
import org.http4s.server.Server
import org.http4s.server.middleware.Logger
import com.comcast.ip4s.{Host, Port}
import org.teckhooi.echo.endpoints.EchoProxyRoutes

import scala.concurrent.duration.DurationInt

object EchoProxy {

  val maxClientConnection = 1000
  def stream[F[_]: Async: Parallel]: Resource[F, Server] = {
    for {
      semaphore <- Resource.eval(Semaphore(maxClientConnection))
      webClient <- `User-Agent`
        .parse("Java/1.8.0_222")
        .fold(
          _ => EmberClientBuilder.default.withoutUserAgent,
          ua => EmberClientBuilder.default.withUserAgent(ua)
        )
        .build
        .map(Commons.limitWebClientConnections(semaphore))

      // attach log to server
      loggedHttpApp = Logger.httpApp[F](true, true)(EchoProxyRoutes.routes[F].orNotFound)

      server <- EmberServerBuilder.default
        .withHost(Host.fromString("0.0.0.0").get)
        .withPort(Port.fromInt(7000).get)
        .withIdleTimeout(1.minute)
        .withHttpApp(loggedHttpApp)
        .build
    } yield server
  }
}
