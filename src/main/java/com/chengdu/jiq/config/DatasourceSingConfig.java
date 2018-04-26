package com.chengdu.jiq.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by jiyiqin on 2017/11/5.
 */

@Configuration
public class DatasourceSingConfig {

    @Autowired
    private Environment env;

    /**
     * 此数据源Bean会自动注入到JdbcTemplate中
     */
    @Bean(name = "singleDataSource")
    public DataSource mysqlDataSource() {
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

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("singleDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
