package com.alibou.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    //changes with the session ID
    @GetMapping("/")
   public String greet(HttpServletRequest request) {
        //basically return the id
        return "Hello World " + request.getSession().getId();
    }
    //I want to do this in postman
}
