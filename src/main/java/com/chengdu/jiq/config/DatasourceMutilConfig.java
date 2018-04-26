package com.chengdu.jiq.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.chengdu.jiq.common.annotation.DbSwitch;
import com.chengdu.jiq.common.dynamicdatasource.DbLookupKey;
import com.chengdu.jiq.common.dynamicdatasource.SqlSessionTemplateDbInterceptor;
import com.chengdu.jiq.common.dynamicdatasource.PlatformDynamicDatasource;
import com.chengdu.jiq.common.dynamicdatasource.TransactionalDbInterceptor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiyiqin on 2017/11/5.
 */

@Configuration
public class DatasourceMutilConfig {

    @Autowired
    private Environment env;

    @Bean(name = "msterDataSource")
    public DataSource msterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        dataSource.setInitialSize(env.getProperty("spring.datasource.initialsize", Integer.class, 10));
        dataSource.setMinIdle(env.getProperty("spring.datasource.minidle", Integer.class, 10));
        dataSource.setMaxActive(env.getProperty("spring.datasource.maxactive", Integer.class, 100));
        dataSource.setMaxWait(env.getProperty("spring.datasource.maxwait", Long.class, 3000L));
        dataSource.setTimeBetweenEvictionRunsMillis(env.getProperty("spring.datasource.checkmillis", Long.class, 60000L));
        dataSource.setMinEvictableIdleTimeMillis(env.getProperty("spring.datasource.minidlemillis", Long.class, 30000L));
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(false);
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
        dataSource.setTestWhileIdle(true);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(30);
        return dataSource;
    }

    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        dataSource.setInitialSize(env.getProperty("spring.datasource.initialsize", Integer.class, 10));
        dataSource.setMinIdle(env.getProperty("spring.datasource.minidle", Integer.class, 10));
        dataSource.setMaxActive(env.getProperty("spring.datasource.maxactive", Integer.class, 100));
        dataSource.setMaxWait(env.getProperty("spring.datasource.maxwait", Long.class, 3000L));
        dataSource.setTimeBetweenEvictionRunsMillis(env.getProperty("spring.datasource.checkmillis", Long.class, 60000L));
        dataSource.setMinEvictableIdleTimeMillis(env.getProperty("spring.datasource.minidlemillis", Long.class, 30000L));
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(false);
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
        dataSource.setTestWhileIdle(true);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(30);
        return dataSource;
    }

    @Bean(name = "dynamicDatasource")
    public PlatformDynamicDatasource dataSource(@Qualifier("msterDataSource") DataSource mater,
                                                @Qualifier("slaveDataSource") DataSource slave) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DbLookupKey.MASTER.name(), mater);
        targetDataSources.put(DbLookupKey.SLAVE.name(), slave);

        PlatformDynamicDatasource dataSource = new PlatformDynamicDatasource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(mater);
        return dataSource;
    }

//    @Bean(name = "TransactionManager-dynamic")
//    public DataSourceTransactionManager oracleTransactionManager(@Qualifier("dynamicDatasource") PlatformDynamicDatasource datasource) {
//        return new DataSourceTransactionManager(datasource);
//    }

    /**
     * 动态数据源切换（切面）
     */

//    @Bean
    public SqlSessionTemplateDbInterceptor sqlSessionTemplateInterceptor() {
        return new SqlSessionTemplateDbInterceptor();
    }

    /**
     * 要么切Service层的自定义DbSwitch注解，要么像这样切SqlSessionTemplate
     * 建议选择前者，后者只适用于mybatis的情况，且无法定制灵活的数据库切换逻辑
     * @return
     */
//    @Bean
    public DefaultPointcutAdvisor sourceSwitchAdvisor() {
        StaticMethodMatcherPointcut pointcut = new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return targetClass == SqlSessionTemplate.class;
            }
        };
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(sqlSessionTemplateInterceptor());
        advisor.setPointcut(pointcut);
        return advisor;
    }

    @Bean
    public TransactionalDbInterceptor transactionalInterceptor() {
        return new TransactionalDbInterceptor();
    }

    @Bean
    public DefaultPointcutAdvisor sourceSwitchAdvisor2() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return targetClass.getAnnotation(DbSwitch.class) != null;
            }
        };
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(transactionalInterceptor());
        advisor.setPointcut(pointcut);
        return advisor;
    }
}
