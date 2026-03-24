package com.example.task.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.task.manager.model.task;

public interface Task_Repository extends JpaRepository<task, Long> {

    List<task> findByUsername(String username);
}   