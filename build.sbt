name := "again"
version := "0.1"
ThisBuild / scalaVersion := "2.13.0"

val akkaVersion = "2.5.25"



lazy val akkaProject = (project in file("akka-example"))
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.google.inject" % "guice" % "4.1.0"),
    mainClass in assembly := Some("app.AkkaApp"),
    scalaVersion := "2.12.8"
  )

lazy val core = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.8" % "test",
      "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
      "io.get-coursier" %% "coursier" % "2.0.0-RC3-3"),
    scalaVersion := "2.13.1"


  )
  .aggregate(akkaProject)



scalacOptions += "-language:higherKinds"

Compile / run / fork := true