package com.chengdu.jiq.service.factorybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jiyiqin on 2017/11/19.
 */
@Configuration
public class FactoryBeanConfig {

    /**
     * 返回的HttpClientFactoryBean对象实际上是返回其getObject()方法返回的对象
     */

    @Bean
    public HttpClientFactoryBean httpClientFactoryBean() {
        HttpClientFactoryBean bean = new HttpClientFactoryBean();
        bean.setAddress("address");
        bean.setOtherAttr("12345678");
        return bean;
    }
}
