package com.example.Logging_server.controller;

import com.example.Logging_server.model.LogEntry;
import com.example.Logging_server.repository.LogRepository;
import com.example.User_server.Token.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LoggingController {
    @Autowired
    private LogRepository logRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LogEntry> logAction(@RequestBody LogEntry logEntry, @RequestHeader("Authorization") String token) {
        String tokenWithoutBearer = token.startsWith("Bearer ") ? token.substring(7) : token;

        // Валидация и извлечение userId из токена
        Claims claims = jwtUtil.extractAllClaims(tokenWithoutBearer);
        Long userIdFromToken = claims.get("userId", Long.class);

        if (userIdFromToken == null || !jwtUtil.validateToken(tokenWithoutBearer, claims.getSubject())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Проверка userId из запроса и userId из токена
        if (logEntry.getUserId() == null || logEntry.getAction() == null || !logEntry.getUserId().equals(userIdFromToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Установка времени и сохранение лога
        logEntry.setTimestamp(LocalDateTime.now());
        LogEntry savedLog = logRepository.save(logEntry);
        return ResponseEntity.ok(savedLog);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<LogEntry>> getUserLogs(
            @PathVariable Long userId,
            @RequestParam String from,
            @RequestParam String to) {
        try {
            LocalDateTime fromDate = LocalDateTime.parse(from);
            LocalDateTime toDate = LocalDateTime.parse(to);
            List<LogEntry> userLogs = logRepository.findByUserIdAndTimestampBetween(userId, fromDate, toDate);
            return ResponseEntity.ok(userLogs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<LogEntry>> getAllLogs(
            @RequestParam String from,
            @RequestParam String to) {
        try {
            LocalDateTime fromDate = LocalDateTime.parse(from);
            LocalDateTime toDate = LocalDateTime.parse(to);
            List<LogEntry> allLogs = logRepository.findByTimestampBetween(fromDate, toDate);
            return ResponseEntity.ok(allLogs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}