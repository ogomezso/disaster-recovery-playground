package org.github.ogomezso.javaproducer.infrastructure.rest;

import java.util.Properties;

import org.github.ogomezso.javaproducer.infrastructure.kafka.ChuckAdapter;
import org.github.ogomezso.javaproducer.infrastructure.kafka.ChuckService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChuckController {

  private final FactResponseMapper mapper = new FactResponseMapper();
  private final ChuckAdapter adapter;

  private final String topic;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public ChuckController(Properties appConfig) {
    this.adapter = new ChuckService(appConfig);
    this.topic = appConfig.getProperty("topic", "chuck-java-topic");
  }

  public String sendFact() throws JsonProcessingException {
    return objectMapper.writeValueAsString(mapper.toResponse(adapter.sendFact(topic)));
  }
}