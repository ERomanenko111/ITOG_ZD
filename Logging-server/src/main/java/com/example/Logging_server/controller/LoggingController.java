package com.example.Logging_server.controller;

import com.example.Logging_server.model.LogEntry;
import com.example.Logging_server.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LoggingController {

    @Autowired
    private LogRepository logRepository;

    @PostMapping
    public ResponseEntity<LogEntry> logAction(@RequestBody LogEntry logEntry) {
        logEntry.setTimestamp(LocalDateTime.now());
        LogEntry savedLog = logRepository.save(logEntry);
        return ResponseEntity.ok(savedLog);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<LogEntry>> getUserLogs(
            @PathVariable Long userId,
            @RequestParam String from,
            @RequestParam String to) {
        LocalDateTime fromDate = LocalDateTime.parse(from);
        LocalDateTime toDate = LocalDateTime.parse(to);
        List<LogEntry> userLogs = logRepository.findByUserIdAndTimestampBetween(userId, fromDate, toDate);
        return ResponseEntity.ok(userLogs);
    }

    @GetMapping
    public ResponseEntity<List<LogEntry>> getAllLogs(
            @RequestParam String from,
            @RequestParam String to) {
        LocalDateTime fromDate = LocalDateTime.parse(from);
        LocalDateTime toDate = LocalDateTime.parse(to);
        List<LogEntry> allLogs = logRepository.findByTimestampBetween(fromDate, toDate);
        return ResponseEntity.ok(allLogs);
    }
}