name := "rscala-kafka"
version := "0.1"

scalaVersion  := "2.11.8"
lazy val kafkaVsersion = "0.10.2.0"

libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % kafkaVsersion
)
