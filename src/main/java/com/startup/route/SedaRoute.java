package com.startup.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SedaRoute extends RouteBuilder {

  public static final String IN = "seda://in";

  public static final String OUT = "seda://out";

  @Override
  public void configure() throws Exception {
    from(IN).to(OUT);
  }
}
