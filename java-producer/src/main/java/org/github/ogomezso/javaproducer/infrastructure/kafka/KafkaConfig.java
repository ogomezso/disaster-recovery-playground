package org.github.ogomezso.javaproducer.infrastructure.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaConfig {

  static KafkaProducer<String, String> createKafkaProducer(Properties appConfig) {
    return new KafkaProducer<>(appConfig);
  }

}
