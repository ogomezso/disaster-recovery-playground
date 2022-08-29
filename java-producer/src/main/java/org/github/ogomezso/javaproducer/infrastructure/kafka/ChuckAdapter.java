package org.github.ogomezso.javaproducer.infrastructure.kafka;

import org.github.ogomezso.javaproducer.domain.model.ChuckFact;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ChuckAdapter {

  ChuckFact sendFact() throws JsonProcessingException;

  ChuckFact SendAvroFact();
}
