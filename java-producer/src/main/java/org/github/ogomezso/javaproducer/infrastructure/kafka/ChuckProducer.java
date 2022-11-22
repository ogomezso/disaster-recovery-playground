package org.github.ogomezso.javaproducer.infrastructure.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChuckProducer {

 private final KafkaProducer<String, String> plainProducer;

  public ChuckProducer(Properties appConfig) {
    this.plainProducer = KafkaConfig.createKafkaProducer(appConfig);
  }

  public void produceJsonMessage(String topic, String msg) {

    ProducerRecord<String, String> record = new ProducerRecord<>(topic, msg);

    plainProducer.send(record, (recordMetadata, exception) -> {
      if (exception == null) {
        log.info("Record written to offset " +
            recordMetadata.offset() + " timestamp " +
            recordMetadata.timestamp());
      } else {
        log.error("An error occurred");
        exception.printStackTrace(System.err);
      }
    });
  }
}
