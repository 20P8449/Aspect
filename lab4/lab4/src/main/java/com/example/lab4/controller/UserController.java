package com.example.lab4.controller;

import com.example.lab4.model.User;
import com.example.lab4.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService svc) {
        this.userService = svc;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user) throws InterruptedException {
        return userService.create(user);
    }
}
