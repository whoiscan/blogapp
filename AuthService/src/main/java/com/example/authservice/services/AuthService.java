package com.example.authservice.services;

import com.example.authservice.model.Response;
import com.example.authservice.model.SignInRequest;
import com.example.authservice.model.SignUpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AuthService {
    Response login(SignInRequest signInRequest) throws JsonProcessingException;

    Response register(SignUpRequest signUpRequest);
}
