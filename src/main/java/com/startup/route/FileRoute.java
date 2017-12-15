package com.startup.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {

  public static final String IN = "file://in";

  public static final String OUT = "file://out";

  @Override
  public void configure() throws Exception {
    from(IN).to(OUT);
  }
}
