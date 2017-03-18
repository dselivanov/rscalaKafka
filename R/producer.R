#' @export
#' @name KafkaProducer
#' @title Creates KafkaProducer object (R6 class)
#' @description Creates KafkaProducer object (R6 class)
#' @section Usage:
#' For usage details see \bold{Methods, Arguments and Examples} sections.
#' \preformatted{
#' producer$send(messages)
#' }
#' @section Methods:
#' \describe{
#'   \item{\code{producer = KafkaProducer$new(topic_name, broker_list, config = list())}}{Creares
#'   instance of KafkaProducer}
#'   \item{\code{producer$send(messages)}}{Asynchronously send a record to a topic.}
#' }
#' @section Arguments:
#' \describe{
#'   \item{producer}{A \code{KafkaProducer} object}
#'   \item{topic_name}{name of a Kafka topic to send messages}
#'   \item{broker_list}{broker list for a Kafka cluster}
#'   \item{config}{ProducerConfig parameters (in \code{key = value} format) for kafka client -
#'   see them here \url{https://kafka.apache.org/0100/javadoc/index.html?org/apache/kafka/clients/producer/ProducerConfig.html}}
#'   \item{messages}{\bold{character} strings - messages to send to kafka}
#' }
KafkaProducer = R6::R6Class(
  classname = "KafkaProducer",
  public = list(
    initialize = function(topic_name,
                          broker_list,
                          config = list()) {
      # ensure these parameters are strings
      broker_list = as.character(broker_list)
      topic_name = as.character(topic_name)

      config_keys = character(0)
      config_values = character(0)

      if(length(config) > 0) {
        config_keys = names(config)
        stopifnot(!is.null(config_keys))

        config_values = unlist(config, use.names = FALSE, recursive = TRUE)
        config_values = as.character(config_values)
        stopifnot(length(config_keys) == length(config_values))
      }

      private$kafka_producer = s$do('org.dselivanov.rkafka.RscalaKafkaProducer')$new(topic_name, broker_list, config_keys, config_values)
    },
    send = function(messages) {
      if(!is.character(messages))
        messages = as.character(messages)
      private$kafka_producer$send(messages, length.one.as.vector=TRUE)
    }
  ),
  private = list(
    kafka_producer = NULL
  )
)
