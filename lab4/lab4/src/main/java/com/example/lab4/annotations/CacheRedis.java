package com.example.lab4.annotations;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRedis {
    String key();
    long ttl();                 // number
    TimeUnit unit() default TimeUnit.SECONDS;
}
