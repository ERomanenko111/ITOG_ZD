package com.example.Logging_server.service;

import com.example.Logging_server.model.Logging;
import com.example.Logging_server.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoggingService {
    @Autowired
    private LogRepository loggingRepository;

    public void logAction(String username, String action, String details) {
        Logging logging = Logging.builder()
                .username(username)
                .timestamp(LocalDateTime.now())
                .action(action)
                .details(details)
                .build();
        loggingRepository.save(logging);
    }

    public List<Logging> getLogsByPeriod(LocalDateTime start, LocalDateTime end) {
        return loggingRepository.findByTimestampPeriod(start, end);
    }

    public List<Logging> getLogsByUser(String username) {
        return loggingRepository.findByUsername(username);
    }

    public List<Logging> getLogsByUserAndPeriod(String username, LocalDateTime start, LocalDateTime end) {
        return loggingRepository.findByUserAndPeriod(username, start, end);
    }

}
