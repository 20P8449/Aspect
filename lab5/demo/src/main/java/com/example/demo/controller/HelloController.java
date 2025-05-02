package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

  @GetMapping("/hello")
  @PreAuthorize("isAuthenticated()")
  public String hello() {
    return "Hello, authenticated user!";
  }
}
