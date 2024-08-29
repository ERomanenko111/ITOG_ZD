package com.example.Logging_server.controller;

import com.example.Logging_server.model.LogEntry;
import com.example.Logging_server.repository.LogRepository;
import com.example.User_server.Token.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<LogEntry> logAction(@RequestBody LogEntry logEntry, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        if(username == null || !jwtUtil.validateToken(token.substring(7), username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (logEntry.getUserId() == null || logEntry.getAction() == null) {
            return ResponseEntity.badRequest().build();
        }
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