package com.example.task.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.task.manager.model.task;
import com.example.task.manager.model.user;
import com.example.task.manager.repository.Task_Repository;
import com.example.task.manager.repository.User_Repository;
import com.example.task.manager.TokenUtil;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private Task_Repository taskRepo;

    @Autowired
    private User_Repository userRepo;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping("/add")
    public String createTask(
            @RequestBody task task,
            @RequestHeader("Authorization") String token) {

        String username = tokenUtil.validateToken(token);

        if (username == null) {
            return "Unauthorized";
        } 

        task.setUsername(username);
        taskRepo.save(task);

        return "Task Added Successfully";
    }

    @GetMapping("/all")
    public Object getTasks(
            @RequestHeader("Authorization") String token) {

        String username = tokenUtil.validateToken(token);

        if (username == null) {
            return "Unauthorized";
        }

        return taskRepo.findByUsername(username);
    }

    @GetMapping("/admin")
    public Object adminTasks(
            @RequestHeader("Authorization") String token) {

        String username = tokenUtil.validateToken(token);

        if (username == null) {
            return "Unauthorized";
        }

        user user = userRepo.findByUsername(username);

        if (user == null) {
            return "Unauthorized";
        }

        if (!user.getRole().equals("ADMIN")) {
            return "Access Denied";
        }

        return taskRepo.findAll();
    }
}