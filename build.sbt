enablePlugins(JavaAppPackaging)

val Http4sVersion          = "0.23.16"
val MunitVersion           = "0.7.20"
val LogbackVersion         = "1.4.3"
val MunitCatsEffectVersion = "1.0.7"
val MeowMTLEffectsVersion  = "0.4.1"
val Log4CatsVersion        = "2.5.0"

lazy val root = (project in file("."))
  .settings(
    organization := "org.teckhooi",
    name := "echo-https",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "3.2.0",
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic"     % LogbackVersion,
      "org.typelevel" %% "log4cats-core"       % Log4CatsVersion,
      "org.typelevel" %% "log4cats-slf4j"      % Log4CatsVersion,
      "org.http4s"    %% "http4s-ember-server" % Http4sVersion,
      "org.http4s"    %% "http4s-ember-client" % Http4sVersion,
      "org.http4s"    %% "http4s-circe"        % Http4sVersion,
      "org.http4s"    %% "http4s-dsl"          % Http4sVersion,
      "org.typelevel" %% "munit-cats-effect-3" % MunitCatsEffectVersion % Test
    ),
    testFrameworks += new TestFramework("munit.Framework")
  )

scalacOptions ++= Seq("-deprecation")

reStart / mainClass := Some("org.teckhooi.echo.Main")

reStart / javaOptions += "-Xmx1g"

console / initialCommands += """import cats._
                                |import cats.effect._
                                |import cats.implicits._
                                """.stripMargin
