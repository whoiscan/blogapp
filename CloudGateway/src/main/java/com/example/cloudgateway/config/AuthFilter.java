package com.example.cloudgateway.config;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
public class AuthFilter implements GatewayFilter {
    @Autowired
    private RouteWhiteList routeWhiteList;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (routeWhiteList.isSecured.test(request)) {
            if (isAuthMissing(request))
                return this.onError(exchange, "Authorization header is missing in request!", HttpStatus.UNAUTHORIZED);

            final String token = getAuthHeader(request);

            if (jwtUtils.isInvalid(token))
                return onError(exchange, "Authorization header is invalid!", HttpStatus.UNAUTHORIZED);
            populateRequestWithHeaders(exchange, token);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtils.getClaimsFromToken(token);
        exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).header("role", String.valueOf(claims.get("role"))).header("username", String.valueOf(claims.get("username"))).build();
    }

}
