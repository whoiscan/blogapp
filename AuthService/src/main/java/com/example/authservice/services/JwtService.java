package com.example.authservice.services;

import com.example.authservice.model.UserResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtService {
    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expire}")
    private Long expireDate;
    private byte[] keyBytes;
    private SecretKey key;

    @PostConstruct
    public void init() {
        keyBytes = secret.getBytes();
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(UserResponse user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("role", "USER");
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expireDate))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
