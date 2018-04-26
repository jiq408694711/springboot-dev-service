package com.chengdu.jiq.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jiyiqin on 2017/9/16.
 */

@Aspect
@Component
public class ControllerAspectInterceptor {

    private final static Logger LOGGER = LoggerFactory.getLogger(ControllerAspectInterceptor.class);

    @Pointcut("@within(com.chengdu.jiq.common.annotation.ControllerAspect)")
    private void gatePointcut() {
    }

    @Pointcut("execution(* com.chengdu.jiq.service..*Controller.*(..))")
    private void portalPointcut() {
    }

    @Around("gatePointcut() || portalPointcut()")
    public Object invokeGate(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        Object args = joinPoint.getArgs();
        LOGGER.info("aop1: BEFORE METHOD CALL, NAME:{}, ARGS:{}", methodName, args);
        Object result = joinPoint.proceed();
        LOGGER.info("aop1: AFTER METHOD CALL, NAME:{}", methodName);
        return result;
    }
}
