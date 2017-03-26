/**
  * Created by dselivanov on 3/9/17.
  */

package org.dselivanov.rkafka

import java.util.Properties
import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.KafkaConsumer


class RscalaKafkaConsumer(val topic_ : Array[String],
                          val bootstrapServers_ : Array[String],
                          val groupId_ : Array[String],
                          val otherPropertiesKeys : Array[String],
                          val otherPropertiesValues : Array[String]) {

  private val topic = topic_(0)
  private val bootstrapServers = bootstrapServers_(0)
  private val groupId = groupId_(0)

  // zip properties to key-value map
  private val propertyMap = otherPropertiesKeys.zip(otherPropertiesValues).toMap

  // add properties
  private def createConsumerConfig(): Properties = {
    val props = new Properties()

    props.put("bootstrap.servers", bootstrapServers)
    props.put("group.id", groupId)

    // default deserializers
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    // add any other properties if any
    propertyMap.map {
      case(propertyKey, propertyValue) => props.put(propertyKey, propertyValue)
    }
    props
  }

  private val props = createConsumerConfig()
  private val consumer = new KafkaConsumer[String, String](props)

  def subscribe(): Unit = {
    consumer.subscribe( List(topic).asJava)
  }

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

//  def setOffset(partition: Int = 0, offset: Long = 0): Unit = {
//    consumer.seek(new TopicPartition(topic, partition), offset)
//  }
//
//  def setOffsetBeginning(partition: Int): Unit = {
//    val tp = new TopicPartition(topic, partition)
//    consumer.seekToBeginning(List(tp).asJava)
//  }
//
//  def setOffsetEnd(partition: Int): Unit = {
//    val tp = new TopicPartition(topic, partition)
//    consumer.seekToEnd(List(tp).asJava)
//  }


}
