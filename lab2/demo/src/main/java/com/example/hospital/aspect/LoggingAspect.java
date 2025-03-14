package com.example.hospital.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Log every request before reaching the controller
    @Before("execution(* com.example.hospital.controller.*.*(..))")
    public void logBeforeControllerMethods(JoinPoint joinPoint) {
        logger.info("📢 [REQUEST] Calling method: " + joinPoint.getSignature().getName());

        // Log request arguments (useful for debugging)
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            logger.info("🔹 Arguments: " + Arrays.toString(args));
        }
    }
}
