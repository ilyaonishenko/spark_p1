
name := "spark_p1"

version := "0.1"

scalaVersion := "2.11.8"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-common" % "2.7.3" % "provided",
  "org.apache.spark" % "spark-core_2.11" % "2.2.0" % "provided",
  "org.apache.spark" % "spark-streaming_2.11" % "2.2.0" % "provided",
  "eu.bitwalker" % "UserAgentUtils" % "1.20",
  "org.apache.spark" % "spark-sql_2.11" % "2.2.0" % "provided"
//  "org.scalatest" %% "scalatest_2.11" % "3.0.3" % "test"
)