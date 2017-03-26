package org.dselivanov.rkafka

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Created by dselivanov on 3/15/17.
  */
class RscalaKafkaProducer(brokers_ : Array[String],
                          clientId_ : Array[String],
                          otherPropertiesKeys: Array[String],
                          otherPropertiesValues: Array[String]
                         ) {
  private val brokers = brokers_(0)
  private val clientId = clientId_(0)

  private val propertyMap = otherPropertiesKeys.zip(otherPropertiesValues).toMap

  // add properties
  private def createProducerConfig(): Properties = {
    val props = new Properties()

    props.put("bootstrap.servers", brokers)
    props.put("client.id", clientId)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    // default deserializers - only strings at the moment
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    // add any other properties if any
    propertyMap.map {
      case(propertyKey, propertyValue) => props.put(propertyKey, propertyValue)
    }

    props
  }

  val props = createProducerConfig()

  val producer = new KafkaProducer[String, String](props)

  def send(message_batch: Array[String], topic_ : Array[String]): Unit = {
    val topic = topic_(0)
    message_batch.map(msg => {
      val data = new ProducerRecord[String, String](topic, msg)
      producer.send(data)
    })
  }

  def close(): Unit = {
    producer.close()
  }
}
