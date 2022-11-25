package net.golovach.eshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;

public class SimpleExceptionLogger {

    public void logException(JoinPoint joinPoint, Throwable t) {
        /*
        Method m = ((MethodSignature)joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        Object target = joinPoint.getTarget();
        */
        System.out.println("ASPECT.EXCEPTION-LOGGER: " + t.getMessage());
    }
}
