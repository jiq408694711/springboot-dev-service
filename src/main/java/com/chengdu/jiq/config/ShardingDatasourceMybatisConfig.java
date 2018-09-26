package com.chengdu.jiq.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Created by jiyiqin on 2017/11/7.
 */

@Configuration
@MapperScan(basePackages = "com.chengdu.jiq.mapper.sharding", sqlSessionTemplateRef = "shardingSqlSessionTemplate")
public class ShardingDatasourceMybatisConfig {

    private static final String LOCATION_MAPPER = "classpath:mybatis/mapper/sharding/*.xml";

    private static final String LOCATION_CONFIG = "classpath:mybatis/mysql-mybatis.xml";

    private static final PathMatchingResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();

    @Bean(name = "shardingSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("shardingdataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(RESOLVER.getResources(LOCATION_MAPPER));
        sqlSessionFactoryBean.setConfigLocation(RESOLVER.getResource(LOCATION_CONFIG));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "shardingSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("shardingSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
