name := """shapeless-playground"""

version := "1.0"

scalaVersion := "2.12.1"
scalaOrganization in ThisBuild := "org.typelevel"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalameta" %% "scalameta" % "1.6.0",
  "com.lihaoyi" % "ammonite" % "0.8.2" % "test" cross CrossVersion.full,
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.typelevel" %% "cats" % "0.9.0"
//  "org.typelevel" %% "shapeless-scalacheck" % "0.4",
//  "org.typelevel" %% "shapeless-spire" % "0.4",
//  "org.typelevel" %% "shapeless-scalaz" % "0.4"
)

initialCommands in (Test, console) := """ammonite.Main().run()"""

fork in run := true