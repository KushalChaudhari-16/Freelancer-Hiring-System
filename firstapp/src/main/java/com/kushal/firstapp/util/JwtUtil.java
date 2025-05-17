package com.kushal.firstapp.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private static final String SECRET = "your_secret_key_your_secret_key_your!"; // 32+ chars
    private static final long EXPIRATION_TIME = 86400000; // 1 day

    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(Long userId, String email, String role, String photoUrl) {
        System.out.println("method runs");
        return Jwts.builder()
                .subject(email)
                .claim("userId", userId) // User ID bhi token me store karna
                .claim("role", role) // Role store karna
                .claim("photoUrl", photoUrl) // Role store karna
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String extractEmail(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY) // New way to verify the key
                .build()
                .parseSignedClaims(token) // New way to parse claims
                .getPayload(); // Replaces getBody()
        return claims.getSubject();
    }
}
