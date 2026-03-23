package com.demo.messanger.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// What is this and why we need it?
// If we worked without a token, it would be inconvenient and slow
// The token is needed so that after logging in you don't have to re-send your password,
// but instead use a secure temporary "pass" that lets the server know who you are
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, Long userId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)                                        //it identifies the user so that when the token comes back later , server know exactly who was it
                .setIssuedAt(new Date())                                          //moment when token was created
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration)) //calculates the "expiry"(3600000 - 1hour)
                .signWith(secretKey, SignatureAlgorithm.HS256)                  //this is the "lock"; it uses secretKey to digitally sign the data
                .compact();                                                    // "wrap"; combine all the data in final string  that the browser can store
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()                // it is a tool that know how to work with original pieces of string
                .setSigningKey(secretKey).build() // before reading name of the token we need to make sure it is original
                .parseClaimsJws(token)           // we have verified everything, and now we can open and see what is inside
                .getBody()                      // see the data inside the token
                .getSubject();                 // we pull the specific username out of that payload

    }

    public Long getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }

}
