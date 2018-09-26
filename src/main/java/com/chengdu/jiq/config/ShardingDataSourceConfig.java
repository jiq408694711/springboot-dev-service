package com.chengdu.jiq.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ShardingDataSourceConfig {

    @Autowired
    private Environment env;

    public DataSource mysqlDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.yiqin.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.yiqin.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.yiqin.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.yiqin.password"));
        dataSource.setInitialSize(env.getProperty("spring.datasource.yiqin.initialsize", Integer.class, 10));
        dataSource.setMinIdle(env.getProperty("spring.datasource.yiqin.minidle", Integer.class, 10));
        dataSource.setMaxActive(env.getProperty("spring.datasource.yiqin.maxactive", Integer.class, 100));
        dataSource.setMaxWait(env.getProperty("spring.datasource.yiqin.maxwait", Long.class, 3000L));
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

    @Bean(name = "shardingdataSource")
    public DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        shardingRuleConfig.getBindingTableGroups().add("t_order");
//        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
//        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new LinkedHashMap<String, Object>(), new Properties());
    }

    public TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_order");
//        result.setActualDataNodes("ds${0..1}.t_order${0..1}");
        result.setActualDataNodes("ds0.t_order_${0..1}");
        result.setKeyGeneratorColumnName("order_id");
        return result;
    }

    public Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
//        result.put("ds0", DataSourceUtil.createDataSource("ds0"));
//        result.put("ds1", DataSourceUtil.createDataSource("ds1"));
        result.put("ds0", mysqlDataSource());
        return result;
    }
}
