name := "caliban-blog-series"

version := "0.1"

scalaVersion := "3.1.1"

libraryDependencies ++= Seq(
  "com.github.ghostdogpr"         %% "caliban-client"                % "1.2.1",
  "com.softwaremill.sttp.client3" %% "http4s-backend"                % "3.5.0",
  "org.http4s"                    %% "http4s-blaze-client"           % "0.23.10"
)

enablePlugins(CalibanPlugin)
