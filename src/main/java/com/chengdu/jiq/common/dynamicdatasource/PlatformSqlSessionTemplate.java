package com.chengdu.jiq.common.dynamicdatasource;

import org.mybatis.spring.SqlSessionTemplate;

/**
 * 不这样包一层，MybatisMutilDatasourceConfig中定义的切面实无法切SqlSessionTemplate的
 */
public class PlatformSqlSessionTemplate extends SqlSessionTemplate {

    private final SqlSessionTemplate sqlSessionTemplate;

    public PlatformSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate.getSqlSessionFactory());
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    /**
     * 保证Mapper对象中的SqlSessionTemplate是数据源动态代理的对象
     * 解决SqlSessionTemplate中错误传入this而造成代理失效
     */
    @Override
    public <T> T getMapper(Class<T> type) {
        return this.getConfiguration().getMapper(type, sqlSessionTemplate);
    }
}
