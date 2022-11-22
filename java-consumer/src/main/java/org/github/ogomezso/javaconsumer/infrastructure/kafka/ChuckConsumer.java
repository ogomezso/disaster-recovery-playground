package org.github.ogomezso.javaconsumer.infrastructure.kafka;

import java.time.Duration;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ChuckConsumer {

  private final KafkaConsumer<String, String> plainConsumer;

  public ChuckConsumer(Properties config) {
    this.plainConsumer = KafkaConfig.createKafkaConsumer(config);
  }

  public void pollMessage() {
    while (true) {
      final ConsumerRecords<String, String> consumerRecords = plainConsumer.poll(Duration.ofMillis(500));

      consumerRecords.forEach(record -> {
        System.out.printf("Consumer Record:(%s, %s, %d, %d), from Topic: %s\n",
            record.key(), record.value(),
            record.partition(), record.offset(), record.topic());
      });
    }
  }

}
