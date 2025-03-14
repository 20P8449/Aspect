package com.example.aspect1.aspectlab1.ProfileAspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    public LoggingAspect() {
        System.out.println("[LOG] LoggingAspect initialized.");
    }

    @Before("execution(* com.example.aspect1.aspectlab1.Controller.*.*(..))") // Matches any subpackage under 'controller'
    public void logBeforeExecution() {
        System.out.println("[LOG] Profile API method is being executed...");
    }
}
