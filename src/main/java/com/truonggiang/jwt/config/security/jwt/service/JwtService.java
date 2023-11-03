package com.truonggiang.jwt.config.security.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtService {

    private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    public String generateToken(String username) {

        Map<String, Objects> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Objects> claims, String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractedUsername(String token) {

        return extractedClaims(token, Claims::getSubject);
    }

    public Date extractedDate(String token) {

        return extractedClaims(token, Claims::getExpiration);
    }

    private <T> T extractedClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractedAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractedAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isExpireToken(String token) {

        return extractedDate(token).before(new Date());
    }

    private Key  getSignKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        String username = extractedUsername(token);

        if (username.equals(userDetails.getUsername()) && !isExpireToken(token)) {

            return true;
        }

        return false;
    }
}
