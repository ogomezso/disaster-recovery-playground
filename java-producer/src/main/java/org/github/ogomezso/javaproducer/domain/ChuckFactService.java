package org.github.ogomezso.javaproducer.domain;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import org.github.ogomezso.javaproducer.domain.model.ChuckFact;

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChuckFactService implements ChuckFactPort {

  private final Faker faker = new Faker();

  @Override
  public ChuckFact buildFact() {
    return ChuckFact.builder()
        .id(UUID.randomUUID().toString())
        .timestamp(Timestamp.from(Instant.now()).getTime())
        .fact(faker.chuckNorris().fact())
        .build();
  }
}
