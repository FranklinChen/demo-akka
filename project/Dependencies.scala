import sbt._

object Library {

  // Versions
  val akkaVersion = "2.2.0-RC2"

  // Libraries
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
}

object Dependencies {

  import Library._

  val demoAkka = List(
    akkaActor
  )
}
