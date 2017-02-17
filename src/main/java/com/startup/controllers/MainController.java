package com.startup.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mgogic on 16.02.17.
 */
@RestController
public class MainController {

  @RequestMapping("/greeting")
  public String greeting() {
    return "jessss";
  }
}
