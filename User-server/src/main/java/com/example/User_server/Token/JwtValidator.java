package com.example.User_server.Token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtValidator {

    public static final String SECRET_KEY = "passwored123";

    public static void main(String[] args) {
        String token = "ваш_jwt_токен";
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            System.out.println("Токен валидный!");
        } catch (Exception e) {
            System.err.println("Токен невалидный: " + e.getMessage());
        }
    }
}