package com.chengdu.jiq.common.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jiyiqin on 2017/10/29.
 */
public class ControllerMethodInterceptor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerMethodInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        Object args = invocation.getArguments();
        LOGGER.info("aop2: BEFORE METHOD CALL, NAME:{}, ARGS:{}", methodName, args);
        Object result = invocation.proceed();
        LOGGER.info("aop2: AFTER METHOD CALL, NAME:{}", methodName);
        return result;
    }
}
