package org.teckhooi.echo.endpoints

import cats.Defer
import cats.effect.kernel.Temporal
import cats.effect.Spawn
import cats.implicits.*
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.teckhooi.echo.endpoints.QueryParamDecoders.*

import scala.concurrent.duration.*

object EchoProxyRoutes {
  def routes[F[_]: Spawn: Temporal]: HttpRoutes[F] = {

    val dsl = new Http4sDsl[F] {}
    import dsl._

    HttpRoutes.of[F] {
      case _ @GET -> Root / "echo" :?
          MessageQueryParamMatcher(message) +&
          DelayMillisQueryParamMatcher(maybeDelay) =>
        for {
          delay <- Spawn[F].pure(0) // maybeDelay.fold(Sync[F].pure(0))(d => Sync[F].pure(d))
          _ <-
            if (delay > 0) Temporal[F].sleep(FiniteDuration(delay, MILLISECONDS))
            else Spawn[F].unit
          ok <- Ok(message)
        } yield ok
    }
  }
}
