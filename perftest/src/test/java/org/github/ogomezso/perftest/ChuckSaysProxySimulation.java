package org.github.ogomezso.perftest;


import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class ChuckSaysProxySimulation extends Simulation {

  Config config = ConfigFactory.load();
  private final HttpProtocolBuilder httpProxyProtocol = http
      .baseUrl(config.getString("proxy.base-url"))
      .inferHtmlResources()
      .acceptHeader("*/*");

  private final ScenarioBuilder scn = scenario("ChuckSaysProxyProducer").repeat(1000).on(
      exec(
          http("Chuck Says with proxy")
              .post("/chuck-says")
      )
  );

  {
    setUp(scn.injectOpen(atOnceUsers(10))).protocols(httpProxyProtocol);
  }
}
