package com.example.Task_server.repository;

import com.example.Task_server.model.Status;
import com.example.Task_server.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);
    @Query("SELECT t FROM Task t WHERE t.status = :status AND t.creationTime BETWEEN :start AND :end")
    List<Task> findByPeriod(@Param("status") Status status,
                            @Param("start") LocalDateTime start,
                            @Param("end") LocalDateTime end);
}
