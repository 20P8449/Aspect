package com.example.lab4.aspect;

import com.example.lab4.annotations.RateLimit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RateLimitingAspect {
    private final RedisTemplate<String, Object> redis;

    public RateLimitingAspect(RedisTemplate<String, Object> redis) {
        this.redis = redis;
    }

    @Around("@annotation(rl)")
    public Object around(ProceedingJoinPoint pjp, RateLimit rl) throws Throwable {
        String key = rl.key();
        Long count = redis.opsForValue().increment(key, 1);
        if (count == 1) {
            redis.expire(key, rl.period(), rl.unit());
        }
        if (count > rl.limit()) {
            throw new RuntimeException("Rate limit exceeded for key: " + key);
        }
        return pjp.proceed();
    }
}
