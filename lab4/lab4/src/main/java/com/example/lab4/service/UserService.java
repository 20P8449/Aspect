package com.example.lab4.service;

import com.example.lab4.annotations.RateLimit;
import com.example.lab4.annotations.RedisLock;
import com.example.lab4.model.User;
import com.example.lab4.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Temporarily disable caching so the rate limiter runs on every call
    @RateLimit(key = "users:all:rate", limit = 5, period = 1, unit = TimeUnit.MINUTES)
    // @CacheRedis(key = "users:all:cache", ttl = 5, unit = TimeUnit.MINUTES)
    public List<User> findAll() {
        return repo.findAll();
    }

    @RedisLock(key = "users:create", timeout = 15, unit = TimeUnit.SECONDS)
    public User create(User u) throws InterruptedException {
        Thread.sleep(10_000);  // simulate slow processing
        return repo.save(u);
    }
}
