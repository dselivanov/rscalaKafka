
# Experimental Apache Kafka client

Package is **very experimental**. Trying [rscala](https://dahl-git.byu.edu/dahl/rscala) pacakge for R <-> Scala integration (and it looks very easy to use and create packages which use Scala code!).

To create simple consumer you need something like:
```r
library(rscalaKafka)
consumer = KafkaConsumer$new(topic_name = "some_topic_name", bootstrap_servers = "localhost:9092", group_id = "some_group_id")

while(TRUE) {
  # each message is a string
  messages = consumer$poll(100)
  size = as.numeric(object.size(messages))/1e6
  message(sprintf("%s read %d messages. Total size is %03f mb", Sys.time(), length(messages), size))
}
```
Note, that constructor for `KafkaConsumer` has last argument `config = list()`. Some can put any `ConsumerConfig` parameters (in `key = value` format) for kafka client - see them in the [Kafka javadoc](https://kafka.apache.org/0100/javadoc/index.html?org/apache/kafka/clients/consumer/ConsumerConfig.html).
