package com.chengdu.jiq.service.forkjoin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ForkJoinPoolFactoryBean;

/**
 * Created by jiyiqin on 2017/11/19.
 */
@Configuration
public class ForkJoinConfig {

    @Bean
    public ForkJoinPoolFactoryBean forkJoinPoolFactoryBean() {
        ForkJoinPoolFactoryBean factoryBean = new ForkJoinPoolFactoryBean();
        return factoryBean;
    }
}
