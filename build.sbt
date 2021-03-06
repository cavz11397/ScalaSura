name := "ScalaSura"

version := "0.1"

scalaVersion := "2.11.6"

libraryDependencies += "org.reactivemongo" %% "reactivemongo" % "1.0.0"

libraryDependencies += "io.spray" %% "spray-util" % "1.3.4"
libraryDependencies += "io.spray" %% "spray-can" % "1.3.4"
libraryDependencies += "io.spray" %% "spray-caching" % "1.3.4" % "provided"
libraryDependencies += "io.spray" %% "spray-http" % "1.3.4"
libraryDependencies += "io.spray" %% "spray-httpx" % "1.3.4"
libraryDependencies += "io.spray" %% "spray-client" % "1.3.4"
libraryDependencies += "io.spray" %% "spray-routing" % "1.3.4"
libraryDependencies += "io.spray" %% "spray-testkit" % "1.3.4"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.32"
//libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.32"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.9"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9"
libraryDependencies += "org.scalamock" %% "scalamock" % "5.1.0"

libraryDependencies += "org.specs2" %% "specs2-core" % "4.10.6"

val AkkaVersion = "2.6.15"
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % AkkaVersion

