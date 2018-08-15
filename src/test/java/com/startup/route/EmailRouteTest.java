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
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@MockEndpoints
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations="classpath:test_application.properties")

public class EmailRouteTest {

  @Autowired
  private ProducerTemplate producerTemplate;

  @EndpointInject(uri = "mock:seda:smtp")
  private MockEndpoint mockCamel;

  @Test
  public void test() throws InterruptedException {

    mockCamel.expectedMessageCount(2);

    String body = "Good Day Future Partner,\n";
    String subject = "My subject";
    String address[] = {"to_email@me.com","to_email2@me.com"};

    for ( int i = 0 ; i<address.length ; i++){

      final Map<String, Object> headers;
      headers = new HashMap<>();
      headers.put("From", "office@me.com");
      headers.put("To", address[i]);
      headers.put("Subject", subject);
      headers.put("contentType", "text/plain;charset=UTF-8");

      producerTemplate.asyncRequestBodyAndHeaders("seda:email", body, headers);
      Thread.sleep(5000);

    }


    mockCamel.assertIsSatisfied();

  }
}
