package org.github.ogomezso.javaconsumer.infrastructure.kafka;

import java.util.Properties;

public class ChuckService implements ConsumerAdapter {

  private final ChuckConsumer consumer;

  public ChuckService(Properties config) {
    this.consumer = new ChuckConsumer(config);
  }

  @Override
  public void pollMessages() {
    consumer.pollMessage();
  }

}
