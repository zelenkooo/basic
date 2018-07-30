package com.startup.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.jsse.KeyStoreParameters;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.apache.camel.util.jsse.TrustManagersParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EmailRoute extends RouteBuilder {

  public static final String IN = "seda://email";

  @Value("${emial.server.smtps.endpoint}")
  public  String OUT;

  @Override
  public void configure() throws Exception {
    from(IN).to(OUT).process(new Processor() {
      @Override
      public void process(Exchange exchange) throws Exception {
        exchange.getIn();
      }
    });
  }

  @Bean
  public SSLContextParameters emailSmtpSslContextParameters(@Value("${email.server.keystore.location}") final String location,
                                                         @Value("${email.server.keystore.password}") final String password){
    KeyStoreParameters store = new KeyStoreParameters();
    store.setResource(location);
    store.setPassword(password);

    TrustManagersParameters trust = new TrustManagersParameters();
    trust.setKeyStore(store);

    SSLContextParameters parameters = new SSLContextParameters();
    parameters.setTrustManagers(trust);

    return parameters;
  }
}
