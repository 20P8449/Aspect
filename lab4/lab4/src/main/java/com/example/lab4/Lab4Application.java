package com.example.lab4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableCaching                   // enable Spring’s @Cacheable support
@EnableAspectJAutoProxy          // enable processing of our @Aspect classes
public class Lab4Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab4Application.class, args);
    }

}
