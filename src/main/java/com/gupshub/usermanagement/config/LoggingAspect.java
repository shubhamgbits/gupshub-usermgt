package com.gupshub.usermanagement.config;

import com.gupshub.usermanagement.util.CommonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.gupshub.usermanagement.controller..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature().getName(), CommonUtil.marshalJson(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.gupshub.usermanagement.controller..*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method: {} with result: {}", joinPoint.getSignature().getName(), CommonUtil.marshalJson(result));
    }
}
