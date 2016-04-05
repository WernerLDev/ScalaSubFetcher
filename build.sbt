name := "Subtitle fetcher"

version := "0.3"

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-deprecation", "-feature")

mainClass in Compile := Some("ScalaSubFetcher")

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "1.0.1"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "org.apache.xmlrpc" % "xmlrpc-client" % "3.1.3"
