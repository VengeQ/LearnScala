name := "again"

version := "0.1"

scalaVersion := "2.13.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"

libraryDependencies += "io.get-coursier" %% "coursier" % "2.0.0-RC3-3"

scalacOptions += "-language:higherKinds"