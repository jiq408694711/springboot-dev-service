package com.chengdu.jiq.service.factorybean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by jiyiqin on 2017/11/19.
 * Spring中的Bean有两种：
 （1）一种是普通的bean ，比如配置
 <bean id="personService" class="com.spring.service.impl.PersonServiceImpl" scope="prototype">
 <property name="name" value="is_zhoufeng" />
 </bean>
 BeanFactory根据id personService获取bean的时候，得到的对象就是PersonServiceImpl类型的。

 （2）另外一种就是实现了{@link org.springframework.beans.factory.FactoryBean}接口的 Bean
 在某些情况下，实例化bean过程比较复杂，如果按照传统的方式，则需要在<bean>中提供大量的配置信息，
 配置方式的灵活性是受限的，这时采用编码的方式可能会得到一个简单的方案。Spring为此提供了一个
 org.Springframework.bean.factory.FactoryBean的工厂类接口，用户可以通过实现该接口定制实例化bean的逻辑。
 FactoryBean接口对于Spring框架来说占有重要的地位，Spring 自身就提供了70多个FactoryBean的实现。
 它们隐藏了实例化一些复杂bean的细节，给上层应用带来了便利。从Spring 3.0 开始， FactoryBean开始支持泛型。

 一句话说，FactoryBean屏蔽了某些bean复杂的实例化细节，直接返回一个复杂的实例化对象。
 常见的应用场景：
 1 返回RPC访问代理类
 2 mybatis返回SqlSessionFactory实例
 */
public class HttpClientFactoryBean implements FactoryBean, InitializingBean, DisposableBean{

    private String address;
    private String otherAttr;
    private HttpClient client;

    @Override
    public void afterPropertiesSet() throws Exception {
        HttpClient client = new HttpClient();
        client.setAddress(this.address);
        client.setOtherAttr(this.otherAttr);
        this.client = client;
    }

    /**
     * 返回HttpClient对象，@Autowired 的地方直接注入此对象
     */
    @Override
    public Object getObject() throws Exception {
        return this.client;
    }

    @Override
    public void destroy() throws Exception { }

    @Override
    public Class<?> getObjectType() {
        return HttpClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOtherAttr() {
        return otherAttr;
    }

    public void setOtherAttr(String otherAttr) {
        this.otherAttr = otherAttr;
    }
}
