package com.startup.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@MockEndpoints
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FileRouteTest {

  @Autowired
  private ProducerTemplate producerTemplate;

  @EndpointInject(uri = "mock:file:out")
  private MockEndpoint mockCamel;

  @Test
  public void test() throws InterruptedException {

    mockCamel.expectedMessageCount(1);
    producerTemplate.sendBody("file:in", "TestBody");
    mockCamel.assertIsSatisfied();

  }
}
