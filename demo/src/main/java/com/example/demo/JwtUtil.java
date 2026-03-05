package com.example.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "clave_super_secreta_2026_clave_super_secreta_2026";

    private Key getKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // 🔹 GENERAR
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(getKey())
                .compact();
    }

    // 🔹 EXTRAER USERNAME
    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    // 🔹 EXTRAER CLAIMS
    private Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 🔹 VALIDAR TOKEN
    public boolean validateToken(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }
}
