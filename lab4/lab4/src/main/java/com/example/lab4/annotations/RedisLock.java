package com.example.lab4.annotations;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {
    String key();
    long timeout() default 30;
    TimeUnit unit() default TimeUnit.SECONDS;
}
