package com.example.authservice.controller;

import com.example.authservice.model.Response;
import com.example.authservice.model.SignInRequest;
import com.example.authservice.model.SignUpRequest;
import com.example.authservice.services.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Response login(@Valid @RequestBody SignInRequest request) throws JsonProcessingException {
        return authService.login(request);
    }

    @PostMapping("/register")
    public Response register(@Valid @RequestBody SignUpRequest request) {
        return authService.register(request);
    }
}
