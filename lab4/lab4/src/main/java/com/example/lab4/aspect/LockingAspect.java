package com.example.lab4.aspect;

import com.example.lab4.annotations.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LockingAspect {
    private final RedisTemplate<String, Object> redisTemplate;

    public LockingAspect(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("@annotation(lock)")
    public Object around(ProceedingJoinPoint pjp, RedisLock lock) throws Throwable {
        String key = lock.key();
        boolean acquired = redisTemplate.opsForValue()
            .setIfAbsent(key, "LOCKED", lock.timeout(), lock.unit());
        if (!acquired) {
            throw new RuntimeException("Could not acquire lock: " + key);
        }
        try {
            return pjp.proceed();
        } finally {
            redisTemplate.delete(key);
        }
    }
}
