package com.startup.route;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
//@MockEndpoints
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@TestPropertySource(locations="classpath:test_application.properties")
@Ignore
public class EmailRouteTest {

  @Autowired
  private ProducerTemplate producerTemplate;

  //@EndpointInject(uri = "mock:seda:out")
  //private MockEndpoint mockCamel;


  @Test
  public void test() throws InterruptedException {


    String body = "Good Day Future Partner,\n";
    String subject = "My subject";
    String address[] = {"to_email@me.com"};

    for ( int i = 0 ; i<address.length ; i++){

      final Map<String, Object> headers;
      headers = new HashMap<>();
      headers.put("From", "office@me.com");
      headers.put("To", address[i]);
      headers.put("Subject", subject);
      headers.put("contentType", "text/plain;charset=UTF-8");
      //mockCamel.expectedMessageCount(1);

      // String body = "Hello Claus.\nYes it does.\n\nRegards James.";
      //producerTemplate.sendBodyAndHeaders("seda:email", body, headers);
      Thread.sleep(10000);
      producerTemplate.asyncRequestBodyAndHeaders("seda:email", body, headers);

    }


  }
}
