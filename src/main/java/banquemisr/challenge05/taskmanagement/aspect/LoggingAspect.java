package banquemisr.challenge05.taskmanagement.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* banquemisr.challenge05.taskmanagement.services.*.*(..))")
    void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Entering Method {}", joinPoint.getSignature().toShortString());
    }

    @After("execution(* banquemisr.challenge05.taskmanagement.services.*.*(..))")
    void logAfterMethod(JoinPoint joinPoint) {
        logger.info("Exiting Method {}", joinPoint.getSignature().toShortString());
    }

}
