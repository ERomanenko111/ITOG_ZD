package com.example.User_server.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    public static final String SECRET_KEY = "passwored123"; // Замените на реальный ключ безопасности
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 час

    public static String generateToken(Long userId, String username) { // Измените userId на Long
        long now = System.currentTimeMillis();

        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT"); // Установите тип JWT
        header.put("alg", "HS256"); // Используйте HS256

        return Jwts.builder()
                .setHeader(header) // Установите заголовок
                .setSubject(username)
                .claim("userId", userId) // Сохраняйте userId как Long
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Используйте HS256
                .compact();
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        // Извлекайте userId как Long
        Long userId = claims.get("userId", Long.class);
        return claims.getSubject();
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}