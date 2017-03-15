#' @import rscala
#' @importFrom R6 R6Class

#' @export
#' @name KafkaConsumer
#' @title Creates KafkaConsumer object (R6 class)
#' @description Creates KafkaConsumer object (R6 class)
#' @section Usage:
#' For usage details see \bold{Methods, Arguments and Examples} sections.
#' \preformatted{
#' consumer$poll(interval = 100)
#' }
#' @section Methods:
#' \describe{
#'   \item{\code{consumer = KafkaConsumer$new(topic_name, bootstrap_servers, group_id, config = list())}}{}
#'   \item{\code{consumer$poll(interval = 100)}}{Fetch data for the topics or partitions specified
#'   using one of the subscribe/assign APIs. On each poll, consumer will try to use the last consumed offset as the starting offset and fetch sequentially.
#'   The last consumed offset can be manually set through #seek(TopicPartition, long) or automatically
#'   set as the last committed offset for the subscribed list of partitions}
#' }
#' @section Arguments:
#' \describe{
#'   \item{consumer}{A \code{KafkaConsumer} object}
#'   \item{topic_name}{name of a Kafka topic to read messages from}
#'   \item{broker_list}{bootstrap_servers for a Kafka cluster}
#'   \item{config}{ConsumerConfig parameters (in \code{key = value} format) for kafka client -
#'   see them here \url{https://kafka.apache.org/0100/javadoc/index.html?org/apache/kafka/clients/consumer/ConsumerConfig.html}}
#'   \item{group_id}{group_id for a Kafka client - will be used to understand offsets}
#'   \item{interval}{\bold{integer >= 0} scalar value. The time, in milliseconds, spent waiting in poll if data is not available in the buffer.
#'   If 0, returns immediately with any records that are available currently in the buffer, else returns empty.}
#' }
KafkaConsumer = R6::R6Class(
  classname = "KafkaConsumer",
  public = list(
    initialize = function(topic_name,
                          bootstrap_servers,
                          group_id,
                          config = list()) {
      # ensure these parameters are strings
      bootstrap_servers = as.character(bootstrap_servers)
      topic_name = as.character(topic_name)
      group_id = as.character(group_id)

      config_keys = character(0)
      config_values = character(0)

      if(length(config) > 0) {
        config_keys = names(config)
        stopifnot(!is.null(config_keys))

        config_values = unlist(config, use.names = FALSE, recursive = TRUE)
        config_values = as.character(config_values)
        stopifnot(length(config_keys) == length(config_values))
      }

      private$kafka_consumer = s$do('org.dselivanov.rkafka.RscalaKafkaConsumer')$new(topic_name, bootstrap_servers, group_id, config_keys, config_values)
    },
    poll = function(interval = 0) {
      interval = as.integer(interval)
      private$kafka_consumer$poll(interval)
    }
  ),
  private = list(
    kafka_consumer = NULL
  )
)
