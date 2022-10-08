package org.teckhooi.echo

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    EchoProxy.stream[IO].use(_ => IO.never).as(ExitCode.Success)
}
