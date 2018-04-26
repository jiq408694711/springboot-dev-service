package com.chengdu.jiq.common.beanprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.lang.reflect.Method;

/**
 * Created by jiyiqin on 2017/11/4.
 */
@Component
public class PlatformBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        LOGGER.info("初始化前置方法: {}[{}]", beanName, bean.getClass().getSimpleName());
        if(bean instanceof FreeMarkerViewResolver) {
            try {
//                Method[] methods = bean.getClass().getSuperclass().getMethods();
//                for(Method method : methods) {
//                    if(method.getName().equals("getPrefix")) {
//                        LOGGER.info("视图解析器前缀:{}", method.invoke(bean));
//                    }
//                    if(method.getName().equals("getSuffix")) {
//                        LOGGER.info("视图解析器后缀:{}", method.invoke(bean));
//                    }
//                }
//                LOGGER.info("视图解析器前缀:{}", (FreeMarkerViewResolver)bean.g));
//                LOGGER.info("视图解析器后缀:{}", method.invoke(bean));
            } catch (Exception e) {
                throw new BeanInitializationException("bean init exception");
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        LOGGER.info("初始化后置方法: {}[{}]", beanName, bean.getClass().getSimpleName());
        return bean;
    }
}
