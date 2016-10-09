import sbt.Keys._

lazy val commonSettings = Seq(
  organization := "com.stronans",
  version := "1.0.0",
  scalaVersion := "2.10.3",
  exportJars := true,
  // This forbids including Scala related libraries into the dependency
  autoScalaLibrary := false,
  // Enables publishing to maven repo
  publishMavenStyle := true,
  // Do not append Scala versions to the generated artifacts
  crossPaths := false
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "messagebus"
  )

libraryDependencies ++= Seq(
  "log4j" % "log4j" % "1.2.16",
  "junit" % "junit" % "4.11" % "test",
  "com.jayway.jsonpath" % "json-path-assert" % "0.8.1" % "test"
)
