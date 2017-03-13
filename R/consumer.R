#' @import rscala
#' @importFrom R6 R6Class
#' @export
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
