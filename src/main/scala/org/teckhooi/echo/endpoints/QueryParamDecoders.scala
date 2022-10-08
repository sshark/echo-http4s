package org.teckhooi.echo.endpoints

import org.http4s.QueryParamDecoder
import org.http4s.dsl.io.{OptionalQueryParamDecoderMatcher, QueryParamDecoderMatcher}

object QueryParamDecoders {
  object MessageQueryParamMatcher extends QueryParamDecoderMatcher[String]("message")
  object DelayMillisQueryParamMatcher extends OptionalQueryParamDecoderMatcher[Int]("delay")
}
