package com.demo.messanger.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("{jwt.expiration}")
    private int jwtExpiration;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) //it identifies the user so that when the token comes back later , server know exactly who was it
                .setIssuedAt(new Date()) //moment when token was created
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration)) //calculates the "expiry"(3600000 - 1hour)
                .signWith(secretKey, SignatureAlgorithm.ES256) //this is the "lock"; it uses secretKey to digitally sign the data
                .compact(); // "wrap"; combine all the data in final string  that the browser can store
    }

    public void getUsername() {
    }

    public void validateToken() {

    }

}
