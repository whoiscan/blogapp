package com.example.cloudgateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackMethodController {
    @GetMapping("/user-service-fallback")
    public String userServiceFallback(){
        return "User Service is not responding...Try again later";
    }
}
