package com.example.User_server.Security;

import com.example.User_server.Token.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static javax.crypto.Cipher.SECRET_KEY;

// JWTAuthorizationFilter для проверки токена после аутентификации
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JWTAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                String username = jwtUtil.extractUsername(token); // Извлекаем username
                // Извлекаем userId из токена
                Claims claims = Jwts.parser()
                        .setSigningKey(String.valueOf(SECRET_KEY))
                        .parseClaimsJws(token)
                        .getBody();
                String userId = claims.get("userId", String.class);

                if (userId != null && jwtUtil.validateToken(token, username)) { // Проверяем токен
                    // Создайте объект Authentication
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userId, // Используйте извлеченный userId
                            null,
                            null
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Обработка исключений (например, невалидный токен)
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
        filterChain.doFilter(request, response);
    }
}