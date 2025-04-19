package com.example.lab4.annotations;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    String key();
    long limit();
    long period();              // number
    TimeUnit unit() default TimeUnit.SECONDS;
}
