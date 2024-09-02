package com.example.Logging_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.Logging_server.model.Logging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Logging, Long> {
    List<Logging> findByUsername(String username);

    @Query("SELECT l FROM Logging l WHERE l.timestamp BETWEEN :start AND :end")
    List<Logging> findByTimestampPeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT l FROM Logging l WHERE l.username = :username AND l.timestamp BETWEEN :start AND :end")
    List<Logging> findByUserAndPeriod(@Param("username") String username, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
