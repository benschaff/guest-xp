import play.Project._

name := "guest-xp"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.2",
  "org.mockito" % "mockito-core" % "1.9.5",
  "org.mockito" % "mockito-all" % "1.9.5",
  "org.webjars" %% "webjars-play" % "2.2.1-2",
  "org.webjars" % "requirejs" % "2.1.8",
  "org.webjars" % "bootstrap" % "3.1.1",
  "org.webjars" % "angularjs" % "1.2.14",
  "org.webjars" % "font-awesome" % "4.0.3",
  "org.webjars" % "jquery" % "2.1.0-2",
  "org.webjars" % "metroui" % "d93d0c5f54"
)

play.Project.playScalaSettings
