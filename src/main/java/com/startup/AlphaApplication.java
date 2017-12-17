package com.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@EnableJpaRepositories
public class AlphaApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(AlphaApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(AlphaApplication.class);
  }

  /*
   Specific configuration needed for the JBoss deployment
   */
  @Bean
  public DispatcherServlet dispatcherServlet() {
    return new DispatcherServlet();
  }

  /*
   Specific configuration needed for the JBoss deployment
   */
  @Bean
  public ServletRegistrationBean dispatcherServletRegistration() {

    ServletRegistrationBean registration = new ServletRegistrationBean(
            dispatcherServlet(), "/*");

    registration
            .setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);

    return registration;
  }
}
