package com.example.lab4.aspect;

import com.example.lab4.annotations.CacheRedis;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheAspect {
    private final RedisTemplate<String, Object> redis;

    public CacheAspect(RedisTemplate<String, Object> redis) {
        this.redis = redis;
    }

    @Around("@annotation(cr)")
    public Object around(ProceedingJoinPoint pjp, CacheRedis cr) throws Throwable {
        String key = cr.key();
        Object cached = redis.opsForValue().get(key);
        if (cached != null) {
            return cached;
        }
        Object result = pjp.proceed();
        redis.opsForValue().set(key, result, cr.ttl(), cr.unit());
        return result;
    }
}
