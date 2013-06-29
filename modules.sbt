lazy val demoAkka = Project(
  "demoAkka",
  file("."),
  settings = Defaults.defaultSettings,
  configurations = Configurations.default :+ com.typesafe.sbt.SbtMultiJvm.MultiJvmKeys.MultiJvm
)
