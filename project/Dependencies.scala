import sbt._

object Library {

  // Versions
  val akkaVersion = "2.2.0-RC2"
  val logbackVersion = "1.0.13"
  val sprayVersion = "1.2-20130628" // Compatible with Akka 2.2.0-RC2

  // Libraries
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val logbackClassic = "ch.qos.logback" % "logback-classic" % logbackVersion
  val sprayCan = "io.spray" % "spray-can" % sprayVersion
}

object Dependencies {

  import Library._

  val demoAkka = List(
    akkaActor,
    akkaSlf4j,
    logbackClassic,
    sprayCan
  )
}
