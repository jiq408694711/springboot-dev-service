package com.chengdu.jiq.common.dynamicdatasource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * Created by jiyiqin on 2017/11/8.
 */
public class TransactionalDbInterceptor implements MethodInterceptor, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionalDbInterceptor.class);

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        final Method method = methodInvocation.getMethod();
        final Transactional transactional = method.getAnnotation(Transactional.class);
        Object result = null;
        try {
            if(null == transactional) {
                LOGGER.info("数据源切换到从库");
                LookupKeyHolder.setLookupKey(DbLookupKey.SLAVE.name());
            } else {
                LOGGER.info("数据源切换到主库");
                LookupKeyHolder.setLookupKey(DbLookupKey.MASTER.name());
            }
            result = methodInvocation.proceed();
        } finally {
            LookupKeyHolder.removeLookupKey();
        }
        return result;
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
