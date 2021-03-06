organization := "net.astail"
name := "crawler"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.11.2",
  "org.skinny-framework" %% "skinny-framework" % "2.5.2",
  "com.typesafe" % "config" % "1.3.3",
  "mysql" % "mysql-connector-java" % "6.0.6",
  "org.scalikejdbc" %% "scalikejdbc" % "3.2.3",
  "com.danielasfregola" %% "twitter4s" % "5.5"
)
