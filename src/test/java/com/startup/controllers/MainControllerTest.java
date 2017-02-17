package com.startup.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MainControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void indexTest() {
    ResponseEntity<String> entity = this.restTemplate.getForEntity("/", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(entity.getHeaders().getContentType())
            .isEqualTo(MediaType.valueOf("text/plain;charset=UTF-8"));
    assertThat(entity.getBody()).contains("Hello World !");
  }

  @Test
  public void testCss() throws Exception {
    ResponseEntity<String> entity = this.restTemplate.getForEntity(
            "/css/style.css", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(entity.getHeaders().getContentType())
            .isEqualTo(MediaType.valueOf("text/css"));
  }

  @Test
  public void testJs() throws Exception {
    ResponseEntity<String> entity = this.restTemplate.getForEntity(
            "/js/init.js", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(entity.getHeaders().getContentType())
            .isEqualTo(MediaType.valueOf("application/javascript"));
  }
}

