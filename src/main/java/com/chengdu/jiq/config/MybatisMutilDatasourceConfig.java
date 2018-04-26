package com.chengdu.jiq.config;

import com.chengdu.jiq.common.dynamicdatasource.PlatformDynamicDatasource;
import com.chengdu.jiq.common.dynamicdatasource.PlatformSqlSessionTemplate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Created by jiyiqin on 2017/11/7.
 */
@Configuration
@MapperScan(basePackages = "com.chengdu.jiq.mapper.dynamic", sqlSessionTemplateRef = "platformSqlSessionTemplate")
public class MybatisMutilDatasourceConfig {

    private static final String LOCATION_MAPPER = "classpath:mybatis/mapper/dynamic/*.xml";

    private static final String LOCATION_CONFIG = "classpath:mybatis/mysql-mybatis.xml";

    private static final PathMatchingResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();

    @Autowired
    private PlatformDynamicDatasource platformDynamicDatasource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(platformDynamicDatasource);
        sqlSessionFactoryBean.setMapperLocations(RESOLVER.getResources(LOCATION_MAPPER));
        sqlSessionFactoryBean.setConfigLocation(RESOLVER.getResource(LOCATION_CONFIG));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean
    public PlatformSqlSessionTemplate platformSqlSessionTemplate() throws Exception {
        return new PlatformSqlSessionTemplate(sqlSessionTemplate());
    }
}
