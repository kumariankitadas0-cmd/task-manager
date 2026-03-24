package com.example.task.manager.controller;


import com.example.task.manager.TokenUtil;
import com.example.task.manager.model.user;
import com.example.task.manager.repository.Task_Repository;
import com.example.task.manager.repository.User_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private User_Repository repo;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private Task_Repository taskRepo;

    @PostMapping("/register")
    public String register(@RequestBody user user){
        repo.save(user);
        return "Registered!!";
    }

    @PostMapping("/login")
    public String login(@RequestBody user user) {

        System.out.println("INPUT USERNAME: " + user.getUsername());
        System.out.println("INPUT PASSWORD: " + user.getPassword());

        user dbUser = repo.findByUsername(user.getUsername());

        System.out.println("DB USER: " + dbUser);

        if (dbUser == null) {
            return "User not found"; 
        }

        if (!dbUser.getPassword().equals(user.getPassword())) {
            return "Invalid password";
        }

        return tokenUtil.generateToken(user.getUsername());
    }
}