package com.example.authservice.services.impl;

import com.example.authservice.model.Response;
import com.example.authservice.model.SignInRequest;
import com.example.authservice.model.SignUpRequest;
import com.example.authservice.model.UserResponse;
import com.example.authservice.services.AuthService;
import com.example.authservice.services.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ObjectMapper objectMapper;


    private final String userServiceUrl = "http://user-service/users";

    @Override
    public Response login(SignInRequest signInRequest) throws JsonProcessingException {
        Response response = restTemplate.postForObject(userServiceUrl + "/login", signInRequest, Response.class);
        if (response == null) return new Response(false, "Could not get response from User Service!", null);
        if (!response.isSuccess() || response.getData() == null) return response;
        UserResponse userResponse = objectMapper.readValue(objectMapper.writeValueAsString(response.getData()), new TypeReference<UserResponse>() {
        });
        String token = jwtService.generateJwtToken(userResponse);
        return new Response(token, true);
    }

    @Override
    public Response register(SignUpRequest signUpRequest) {
        Response response = restTemplate.postForObject(userServiceUrl, signUpRequest, Response.class);
        if (response == null) return new Response(false, "Could not get response from User Service!", null);
        return response;
    }
}
