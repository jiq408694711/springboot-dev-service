package com.chengdu.jiq.config;

import com.chengdu.jiq.common.annotation.ControllerAspect;
import com.chengdu.jiq.common.aop.ControllerMethodInterceptor;
import com.chengdu.jiq.common.interceptor.ControllerInterceptor;
import com.chengdu.jiq.common.utils.JsonConvertor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.lang.reflect.Method;

/**
 * Spring-boot-starter-freemarker配置直接使用
 * 配置见application.properties中的## Freemarker 配置部分
 * <p>
 * Spring Boot provides auto-configuration for Spring MVC that works well with most applications.
 * <p>
 * The auto-configuration adds the following features on top of Spring’s defaults:
 * 1.Inclusion of ContentNegotiatingViewResolver and BeanNameViewResolver beans.
 * 2.Support for serving static resources, including support for WebJars (see below).
 * 3.Automatic registration of Converter, GenericConverter, Formatter beans.
 * 4.Support for HttpMessageConverters (see below).
 * 5.Automatic registration of MessageCodesResolver (see below).
 * 6.Static index.html support.
 * 7.Custom Favicon support (see below).
 * 8.Automatic use of a ConfigurableWebBindingInitializer bean (see below).
 * <p>
 * If you want to keep Spring Boot MVC features, and you just want to add additional
 * MVC configuration (interceptors, formatters, view controllers etc.) you can add
 * your own @Configuration class of type WebMvcConfigurerAdapter, but without @EnableWebMvc.
 * <p>
 * If you wish to provide custom instances of RequestMappingHandlerMapping,
 * RequestMappingHandlerAdapter or ExceptionHandlerExceptionResolver you can declare
 * a WebMvcRegistrationsAdapter instance providing such components.
 * <p>
 * If you want to take complete control of Spring MVC, you can add your own @Configuration
 * annotated with @EnableWebMvc.
 */
@Configuration
//@EnableWebMvc //不需要，Springboot支持
public class WebMvcConfig extends WebMvcConfigurerAdapter implements InitializingBean {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    /**
     * ViewResolver的配置不需要，Springboot中可以配置在properties中
     *
     * @return
     */
//    @Bean
//    public FreeMarkerViewResolver freeMarkerViewResolver() {
//        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
//        resolver.setPrefix("/WEB-INF/templates/");
//        resolver.setSuffix(".jsp");
//        resolver.setContentType("text/html;charset=UTF-8");
//        resolver.setExposeSpringMacroHelpers(true);
//        resolver.setExposeRequestAttributes(true);
//        resolver.setAllowRequestOverride(true);
//        return resolver;
//    }
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable(); //配置静态文件处理
//    }

    /**
     * 配置AOP切面
     * 常用的Pointcut包括：
     * org.springframework.aop.support.NameMatchMethodPointcut
     * org.springframework.aop.support.JdkRegexpMethodPointcut
     * org.springframework.aop.support.Perl5RegexpMethodPointcut
     * org.springframework.aop.support.ExpressionPointcut
     */
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        //Pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return targetClass.getAnnotation(ControllerAspect.class) != null;
            }
        };

        //Advice
        ControllerMethodInterceptor advice = new ControllerMethodInterceptor();

        //Advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(advice);
        return advisor;
    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ControllerInterceptor());
    }

    /**
     * SpringMvc中的@ResponseBody注解返回JSON时可以配置Json转换器
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        HttpMessageConverters converters = messageConverters.getObject();
        converters.getConverters().stream().forEach(convert -> {
            if (convert instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter) convert).setObjectMapper(JsonConvertor.getObjectMapper());
            }
        });
    }
}