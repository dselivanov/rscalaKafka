package org.dselivanov.rkafka
import java.util.Properties

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Created by dselivanov on 3/15/17.
  */
class RscalaKafkaProducer(topic:String,
                          brokers: String,
                          otherPropertiesKeys: Array[String],
                          otherPropertiesValues: Array[String]
                         ) {
  private val propertyMap = otherPropertiesKeys.zip(otherPropertiesValues).toMap
  // add properties
  private def createProducerConfig(): Properties = {
    val props = new Properties()

    props.put("bootstrap.servers", brokers)
    props.put("client.id", "ScalaProducerExample")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    // default deserializers - can be overrwritten in map below
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    propertyMap.map {
      case(propertyKey, propertyValue) => props.put(propertyKey, propertyValue)
    }
    props
  }

  val props = createProducerConfig()

  val producer = new KafkaProducer[String, String](props)

  def send(message_batch: Array[String]): Unit = {
    message_batch.map(msg => {
      val data = new ProducerRecord[String, String](topic, msg)
      producer.send(data)
    })
  }
}
