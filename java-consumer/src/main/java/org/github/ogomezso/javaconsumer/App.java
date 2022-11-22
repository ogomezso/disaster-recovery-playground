package org.github.ogomezso.javaconsumer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import org.github.ogomezso.javaconsumer.infrastructure.kafka.ChuckService;
import org.github.ogomezso.javaconsumer.infrastructure.kafka.ConsumerAdapter;

public class App {

  public static void main(String[] args) {

    try (InputStream input = new FileInputStream(args[0])) {
      Properties config = new Properties();
      config.load(input);
      ConsumerAdapter chuckAdapter = new ChuckService(config);
      chuckAdapter.pollMessages();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
