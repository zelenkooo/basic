package com.startup.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotifyRoute extends RouteBuilder {

  @Value("${camel.notify.in}")
  public String IN;

  @Value("${camel.notify.out}")
  public String OUT;

  @Override
  public void configure() throws Exception {

    from(IN).convertBodyTo(String.class).aggregate(constant(true), new AggregationStrategy() {
      @Override
      public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        classifyData(newExchange);

        if (oldExchange == null) {
          return newExchange;
        }

        // copy headers
        oldExchange.getIn().getHeaders().putAll(newExchange.getIn().getHeaders());

        return oldExchange;

      }
    }).completionSize(3)

            .split(header("data").tokenize("\n"))

            .process(new Processor() {
              @Override
              public void process(Exchange exchange) throws Exception {
                // Store data to the database
                System.out.println("Body : " + exchange.getIn().getBody());
              }
            })
            .process(new Processor() {
              @Override
              public void process(Exchange exchange) throws Exception {
                // Populate template
              }
            })
            .process(new Processor() {
              @Override
              public void process(Exchange exchange) throws Exception {
                // Send email template
              }
            })
            .process(new Processor() {
              @Override
              public void process(Exchange exchange) throws Exception {
                // Update state of the message
              }
            }).to(OUT);


    //from(files).aggregate(mergemessages).process(savetoDb).split().to(email).process(updatedb);

  }


  private void classifyData(Exchange exchange) {
    Map<String, Object> headers = exchange.getIn().getHeaders();
    if (exchange.getIn().getHeaders().get(Exchange.FILE_NAME).equals("template.html")) {
      headers.put("template", exchange.getIn().getBody(String.class));
    }

    if (exchange.getIn().getHeaders().get(Exchange.FILE_NAME).equals("data.csv")) {
      headers.put("data", exchange.getIn().getBody(String.class));
    }

    if (exchange.getIn().getHeaders().get(Exchange.FILE_NAME).equals("subject.txt")) {
      headers.put("subject", exchange.getIn().getBody(String.class));
    }

  }


}
