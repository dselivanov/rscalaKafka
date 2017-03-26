
# Experimental Apache Kafka client

**Very experimental** [rscala](https://dahl-git.byu.edu/dahl/rscala) based Kafka client.

This client is built for `Scala 2.11.8` and `Kafka 0.10.2.0`. For instructions on how to build KafkaClient for your environment please refer to [readme](./rscala-kafka/README.md) in `rscala-kafka` directory.

## Simple setup
To create simple sett you need something like:
```r
library(rscalaKafka)
producer = KafkaProducer$new(broker_list = "localhost:9092", 
                             config = list())
consumer = KafkaConsumer$new(topic = "test", 
                             bootstrap_servers = "localhost:9092", 
                             group_id = "dummy", 
                             config = list("max.poll.records" =  "2"))

producer$send(LETTERS[1:10], "test")

for (i in 1:5) 
  message(consumer$poll(100))
#AB
#CD
#EF
#GH
#IJ

consumer$unsubsribe()
producer$close()

```
* Constructor for `KafkaConsumer` has last argument `config = list()`. Some can put any `ConsumerConfig` parameters (in `key = value` format) for kafka client - see them in the [Kafka ConsumerConfig javadoc](https://kafka.apache.org/0100/javadoc/index.html?org/apache/kafka/clients/consumer/ConsumerConfig.html).

* Constructor for `KafkaProducer` has last argument `config = list()`. Some can put any `ProducerConfig` parameters (in `key = value` format) for kafka client - see them in the [Kafka ProducerConfig javadoc](https://kafka.apache.org/0100/javadoc/index.html?org/apache/kafka/clients/producer/ProducerConfig.html)

**NOTE(!). If you will interrupt loop above with `CTRL+C`/`Esc` `R` <-> `Scala` protocol will be corrupted and most probably you will need to restart `R` and `rscalaKafka` to fix the problem.**
