package org.teckhooi.echo

import cats.Monad
import cats.effect.std.Semaphore
import cats.effect.{Concurrent, MonadCancel, Resource}
import cats.implicits.*
import org.http4s.client.Client

object Commons {

  type MC[F[_]] = MonadCancel[F, Throwable]
  def limitWebClientConnections[F[_]: Concurrent: MC](s: Semaphore[F])(
      client: Client[F]
  ): Client[F] = Client(req => Resource.make(s.acquire)(_ => s.release) >> client.run(req))
}
