package com.startup.route;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MyRouteTest extends CamelTestSupport {

  @Autowired
  private CamelContext camelContext;

  @Test
  public void shouldSucceed() throws Exception {
    // we expect that one or more messages is automatic done by the Camel
    // route as it uses a timer to trigger
    NotifyBuilder notify = new NotifyBuilder(camelContext).whenDone(1).create();

    assertTrue(notify.matches(10, TimeUnit.SECONDS));
  }
}
