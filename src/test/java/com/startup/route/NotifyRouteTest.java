package com.startup.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@MockEndpoints
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations="classpath:test_application.properties")
public class NotifyRouteTest {

  @Autowired
  private ProducerTemplate producerTemplate;

  @EndpointInject(uri = "mock:file:notifyout")
  private MockEndpoint mockCamel;

  @Test
  public void test() throws InterruptedException {

    mockCamel.expectedMessageCount(1);

    producerTemplate.sendBodyAndHeader("file:notifyin", "Message Template : to {{$email}}", Exchange.FILE_NAME, "template.html");
    producerTemplate.sendBodyAndHeader("file:notifyin", "email1@test.com\nemail2@test.com",Exchange.FILE_NAME, "data.csv");
    producerTemplate.sendBodyAndHeader("file:notifyin", "Subject",Exchange.FILE_NAME, "subject.txt");

    mockCamel.assertIsSatisfied();

    mockCamel.getExchanges().get(0).getIn().getBody();
    System.out.println();

  }
}
