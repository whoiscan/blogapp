package com.example.cloudgateway.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class RouteWhiteList {
    public static final Map<String, String> openApiEndpoints = Map.of(
            "/auth/register", "POST",
            "/auth/login", "POST",
            "/posts", "GET"
    );

    public boolean isSecured(ServerHttpRequest request) {
        return openApiEndpoints.entrySet().stream().noneMatch(e -> request.getURI().getPath().equals(e.getKey())
                && Objects.requireNonNull(request.getMethod()).name().equals(e.getValue()));
    }

}
