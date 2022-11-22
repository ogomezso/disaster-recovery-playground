package org.github.ogomezso.javaconsumer.infrastructure.kafka;

import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaConfig {

  static KafkaConsumer<String, String> createKafkaConsumer(Properties config) {

    final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);

    Pattern topicPattern = Pattern.compile(config.getProperty("topic", "*chuck-java-topic"));

    consumer.subscribe(topicPattern);

    return consumer;
  }
}
