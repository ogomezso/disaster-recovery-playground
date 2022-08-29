package org.github.ogomezso.javaproducer.infrastructure.rest;

import org.github.ogomezso.javaproducer.domain.model.ChuckFact;
import org.github.ogomezso.javaproducer.infrastructure.rest.model.ChuckFactResponse;

class FactResponseMapper {

  ChuckFactResponse toResponse(ChuckFact fact) {
    return ChuckFactResponse.builder()
        .fact(fact.getFact())
        .timestamp(fact.getTimestamp())
        .build();
  }
}
