package com.example.task.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.task.manager.model.user;

public interface User_Repository extends JpaRepository<user, Long> {
    user findByUsername(String username);
}   