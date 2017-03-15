
# Experimental Apache Kafka client

Package is **very experimental**. Trying [rscala](https://dahl-git.byu.edu/dahl/rscala) pacakge for R <-> Scala integration (and it looks very easy to use and create packages which use Scala code!).

This client is built for `Scala 2.11.8` and `Kafka 0.10.2.0`. For instructions on how to build KafkaClient for your environment please refer to [readme](./rscala-kafka/README.md) in `rscala-kafka` directory.

## Consumer
To create simple consumer you need something like:
```r
library(rscalaKafka)
consumer = KafkaConsumer$new(topic_name = "some_topic_name", 
bootstrap_servers = "localhost:9092", group_id = "some_group_id", config = list())

while(TRUE) {
  # each message is a string
  messages = consumer$poll(1000)
  size = as.numeric(object.size(messages))/1e6
  message(sprintf("%s read %d messages. Total size is %03f mb", Sys.time(), length(messages), size))
}
```
Constructor for `KafkaConsumer` has last argument `config = list()`. Some can put any `ConsumerConfig` parameters (in `key = value` format) for kafka client - see them in the [Kafka ConsumerConfig javadoc](https://kafka.apache.org/0100/javadoc/index.html?org/apache/kafka/clients/consumer/ConsumerConfig.html).

**NOTE(!). If you will interrupt loop above with `CTRL+C`/`Esc` `R` <-> `Scala` protocol will be corrupted and most probably you will need to restart `R` and `rscalaKafka` to fix the problem.**

## Producer

Create producer with:

```r
library(rscalaKafka)
producer = KafkaProducer$new(topic_name = "some_topic_name", 
bootstrap_servers = "localhost:9092", config = list())
producer$send(LETTERS)
```

Constructor for `KafkaProducer` has last argument `config = list()`. Some can put any `ProducerConfig` parameters (in `key = value` format) for kafka client - see them in the [Kafka ProducerConfig javadoc](https://kafka.apache.org/0100/javadoc/index.html?org/apache/kafka/clients/producer/ProducerConfig.html)
