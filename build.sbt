organization := "name.heikoseeberger"

name := "demo-akka"

version := "1.0.0"

scalaVersion := "2.10.2"

libraryDependencies ++= Dependencies.demoAkka

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-language:_",
  "-target:jvm-1.6",
  "-encoding", "UTF-8"
)
