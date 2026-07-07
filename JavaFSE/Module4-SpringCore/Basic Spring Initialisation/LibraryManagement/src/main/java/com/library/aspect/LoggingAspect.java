package com.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * LoggingAspect — cross-cutting concern for logging.
 *
 * EX3: Logs method execution time using @Around advice.
 * EX8: Adds @Before and @After advice for entry/exit logging.
 *
 * @Aspect marks this as an AspectJ aspect.
 * @Component ensures Spring registers it as a bean (needed for AOP proxying).
 */
@Aspect   // EX3 / EX8
@Component // EX6 — picked up by component scanning
public class LoggingAspect {

    // ---------------------------------------------------------------
    // EX8 — @Before advice: logs method entry before execution
    // ---------------------------------------------------------------

    /**
     * Runs before any method in com.library and its sub-packages.
     */
    @Before("execution(* com.library..*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        System.out.println("[LOG] Entering  >> "
                + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName() + "()");
    }

    // ---------------------------------------------------------------
    // EX8 — @After advice: logs method exit after execution
    // ---------------------------------------------------------------

    /**
     * Runs after any method in com.library (whether it returned or threw).
     */
    @After("execution(* com.library..*(..))")
    public void logMethodExit(JoinPoint joinPoint) {
        System.out.println("[LOG] Exiting   << "
                + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName() + "()");
    }

    // ---------------------------------------------------------------
    // EX3 — @Around advice: measures and logs execution time
    // ---------------------------------------------------------------

    /**
     * Wraps every method in com.library.service and com.library.repository
     * to measure and print elapsed execution time.
     */
    @Around("execution(* com.library.service..*(..)) || execution(* com.library.repository..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Proceed with the actual method call
        Object result = joinPoint.proceed();

        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.println("[LOG] Execution time >> "
                + joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName()
                + "() completed in " + elapsedTime + " ms");

        return result;
    }
}
