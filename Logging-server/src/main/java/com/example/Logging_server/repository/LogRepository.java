package com.example.Logging_server.repository;

import com.example.Logging_server.model.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntry, Long> {
    List<LogEntry> findByUserIdAndTimestampBetween(Long userId, LocalDateTime from, LocalDateTime to);
    List<LogEntry> findByTimestampBetween(LocalDateTime from, LocalDateTime to);
}