package com.chengdu.jiq;

import com.chengdu.jiq.common.utils.JsonConvertor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @EnableAspectJAutoProxy 开启AspectJ代理，并将proxyTargetClass置为true，表示启用cglib对Class进行代理
 * @SpringBootApplication is equivalent to using @Configuration, @EnableAutoConfiguration and @ComponentScan
 * <p>
 * 导入自定义配置文件可用@PropertySource，但是：
 * While using @PropertySource on your @SpringBootApplication seems convenient and easy enough to load
 * a custom resource in the Environment, we do not recommend it as Spring Boot prepares the Environment
 * before the ApplicationContext is refreshed. Any key defined via @PropertySource will be loaded too
 * late to have any effect on auto-configuration.
 * <p>
 * 导入其他配置类的方法：
 * 15.1 Importing additional configuration classes
 * You don’t need to put all your @Configuration into a single class. The @Import annotation can be
 * used to import additional configuration classes. Alternatively, you can use @ComponentScan to
 * automatically pick up all Spring components, including @Configuration classes.
 * 15.2 Importing XML configuration
 * If you absolutely must use XML based configuration, we recommend that you still start with
 * a @Configuration class. You can then use an additional @ImportResource annotation to load
 * XML configuration files.
 */
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan(basePackageClasses = SpringbootDevServiceApplication.class)
//SpringBoot中以上三个注解被@SpringBootApplication替代

//@PropertySource({"classpath:/config/condition.properties"})
//@Import(FactoryBeanConfig.class)
//@ImportResource(locations={"classpath:/xml/client.xml"})
//以上注解引入外部资源
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class SpringbootDevServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDevServiceApplication.class, args);
    }

    @Bean
    public JsonConvertor jsonConvertor() {
        return new JsonConvertor();
    }
}
