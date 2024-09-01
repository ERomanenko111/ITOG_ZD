package com.example.User_server;

import com.example.User_server.Token.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private SecretKey secretKey;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
        jwtUtil.init();
        secretKey = jwtUtil.secretKey; // Используем инициализированный ключ
    }

    @Test
    public void testTokenExpiration() {
        String token = Jwts.builder()
                .setSubject("johndoe")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 2000)) // 2 секунды
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        try {
            Thread.sleep(3000); // 3 секунды, чтобы гарантировать истечение
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean isExpired = jwtUtil.validateToken(token, "johndoe");
        assertFalse(isExpired);
    }
}
