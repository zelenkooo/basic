package com.startup.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Basic Security configuratio
 * Created by mgogic on 15.08.18.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig
        extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
            .ignoring()
            .antMatchers("/js/**")
            .antMatchers("/css/**")
            .antMatchers("/api/**");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    // enable in memory based authentication with a user named "user" and "admin"
    auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("USER")
        .and().withUser("admin").password("{noop}password").roles("USER", "ADMIN");
  }


}
