package com.startup.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

//@RunWith(CamelSpringBootRunner.class)
//@SpringBootTest
@Ignore
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MyRouteTest extends CamelTestSupport {

  //@Autowired
  //private CamelContext camelContext;

  @Produce(uri = MyRoute.IN)
  private ProducerTemplate producerTemplate;

  @EndpointInject(uri = "mock:file:out")
  private MockEndpoint mockEndpointOut;

  @Override
  public String isMockEndpoints() {
    return "*";
  }

  @Test
  public void simpleTest() throws Exception {
    mockEndpointOut.expectedMessageCount(1);
    producerTemplate.sendBody("Test");
    mockEndpointOut.assertIsSatisfied();
  }

  @Override
  protected RoutesBuilder createRouteBuilder() throws Exception {
    return new MyRoute();
  }

}
