package org.github.ogomezso.javaproducer;

import static spark.Spark.port;
import static spark.Spark.post;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import org.github.ogomezso.javaproducer.infrastructure.rest.ChuckController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  public static void main(String[] args) {

    try (InputStream input = new FileInputStream(args[0])) {
      Properties config = new Properties();
      config.load(input);
      ChuckController controller = new ChuckController(config);
      port(8080);
      post("/chuck-says", (req, res) -> {
        log.info("Plain Json request received");
        res.header("Content-Type", "application/json");
        return controller.sendFact();
      });
    } catch (IOException ex) {
      ex.printStackTrace();
    }

  }
}

