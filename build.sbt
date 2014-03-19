import play.Project._

name := "guest-xp"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.2",
  "org.mockito" % "mockito-core" % "1.9.5",
  "org.mockito" % "mockito-all" % "1.9.5"
)

play.Project.playScalaSettings
