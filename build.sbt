name := "ScalaSura"

version := "0.1"

scalaVersion := "2.11.6"

libraryDependencies += "org.reactivemongo" %% "reactivemongo" % "1.0.0"

libraryDependencies += "io.spray" %% "spray-util" % "1.3.4"
libraryDependencies += "io.spray" %% "spray-can" % "1.3.4"
libraryDependencies += "io.spray" %% "spray-caching" % "1.3.4" % "provided"
libraryDependencies += "io.spray" %% "spray-http" % "1.3.4"
libraryDependencies += "io.spray" %% "spray-httpx" % "1.3.4"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.32"
libraryDependencies += "io.spray" %% "spray-client" % "1.3.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"
