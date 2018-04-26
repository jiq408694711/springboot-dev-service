package com.chengdu.jiq.common.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * mybatis拦截器, 配置在mysql-mybatis配置文件中
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MyBatisTraceInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisTraceInterceptor.class);
    private static final long SLOW_SQL_TIME_LENGTH = 1000;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final StopWatch watch = new StopWatch();
        watch.start();
        Object result = null;
        Object[] args = invocation.getArgs();
        final MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameter = args.length > 1 ? args[1] : null;
        final String aliasName = mappedStatement.getId();
        final BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        try {
            result = invocation.proceed();
        } finally {
            watch.stop();
            LOGGER.debug("aliasName={}|sql={}|elapsedTime={}", aliasName, boundSql.getSql(), watch.getTotalTimeMillis());
            if (watch.getTotalTimeMillis() > SLOW_SQL_TIME_LENGTH) {
                final Configuration configuration = mappedStatement.getConfiguration();
                showSql(configuration, boundSql);
                LOGGER.warn("SLOW_SQL, aliasName={}|sql={}|elapsedTime={}", aliasName, boundSql, watch.getTotalTimeMillis());
            }
        }
        return result;
    }

    private String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        final String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (!CollectionUtils.isEmpty(parameterMappings) && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                return sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                return fillArgements(metaObject, parameterMappings, boundSql, sql);
            }
        } else {
            return sql;
        }
    }

    private String fillArgements(MetaObject metaObject, List<ParameterMapping> parameterMappings,
                                 BoundSql boundSql, final String sql) {
        String currentSql = sql;
        for (ParameterMapping parameterMapping : parameterMappings) {
            String propertyName = parameterMapping.getProperty();
            if (metaObject.hasGetter(propertyName)) {
                Object obj = metaObject.getValue(propertyName);
                currentSql = sql.replaceFirst("\\?", getParameterValue(obj));
            } else if (boundSql.hasAdditionalParameter(propertyName)) {
                Object obj = boundSql.getAdditionalParameter(propertyName);
                currentSql = sql.replaceFirst("\\?", getParameterValue(obj));
            }
        }
        return currentSql;
    }

    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(
                    DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // empty implementation
    }

}