/**
  * Created by dselivanov on 3/9/17.
  */

package org.dselivanov.rkafka

import java.util.{Collections, Properties}
import scala.collection.JavaConverters._
//import kafka.utils.Logging
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}


class RscalaKafkaConsumer(val topic: String,
                          val bootstrapServers: String,
                          val groupId: String,
                          val otherPropertiesKeys: Array[String],
                          val otherPropertiesValues: Array[String]) {

  // zip properties to key-value map
  private val propertyMap = otherPropertiesKeys.zip(otherPropertiesValues).toMap

  // add properties
  private def createConsumerConfig(): Properties = {
    val props = new Properties()

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)

    // default deserializers - can be overrwritten in map below
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")

    propertyMap.map {
      case(propertyKey, propertyValue) => props.put(propertyKey, propertyValue)
    }
    props
  }

  private val props = createConsumerConfig()
  private val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(Collections.singletonList(topic))

  def poll(pollTime: Int =  0):Array[String] = {
    consumer
      .poll(pollTime)
      .asScala
      .map(_.value().toString)
      .toArray
   }

  def unsubscribe(): Unit = {
    consumer.unsubscribe()
  }
}
