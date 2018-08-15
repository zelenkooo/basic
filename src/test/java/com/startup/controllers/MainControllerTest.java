package com.startup.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MainControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void indexTest() {
    ResponseEntity<String> entity = restTemplate.getForEntity("/", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(entity.getHeaders().getContentType())
            .isEqualTo(MediaType.valueOf("text/html;charset=UTF-8"));
    assertThat(entity.getBody()).contains("Hello World !");
  }

  @Test
  public void testCss() throws Exception {
    ResponseEntity<String> entity = restTemplate.getForEntity(
            "/css/style.css", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(entity.getHeaders().getContentType())
            .isEqualTo(MediaType.valueOf("text/css"));
  }

  @Test
  public void testJs() throws Exception {
    ResponseEntity<String> entity = restTemplate.getForEntity(
            "/js/init.js", String.class);
    assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(entity.getHeaders().getContentType())
            .isEqualTo(MediaType.valueOf("application/javascript"));
  }
}

