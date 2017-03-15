# Simple Scala consumer/producer

This is SBT Scala project. 

In order to build jars for your setup (and use with `rscalaKafka` package), please make following steps:

1. change **`scalaVersion`** and in **`kafkaVsersion`** variables in [build.sbt](./build.sbt) 
1. build "fat" jar with `assembly`:
```sh
sbt assembly
```
1. put them to r_package_root/inst/java:
```sh
cp target/YOUR_SCALA_VERSION_TARGET/rscala-kafka-assembly-0.1.jar ../inst/java
```
