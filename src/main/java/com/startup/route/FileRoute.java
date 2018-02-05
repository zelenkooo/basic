package com.startup.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {

  @Value("${camel.fileroute.in}")
  public String IN;

  @Value("${camel.fileroute.out}")
  public String OUT;

  @Override
  public void configure() throws Exception {
    from(IN).to(OUT);
  }
}
