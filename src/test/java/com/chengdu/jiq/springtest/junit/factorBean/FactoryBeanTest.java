package com.chengdu.jiq.springtest.junit.factorBean;

import com.chengdu.jiq.SpringbootDevServiceApplication;
import com.chengdu.jiq.service.factorybean.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * Created by jiyiqin on 2017/9/19.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringbootDevServiceApplication.class})
@TestPropertySource("/application.properties")
@WebAppConfiguration
public class FactoryBeanTest {

    /**
     * 复杂的实例化细节被HttpClientFactoryBean所屏蔽
     * 该bean定义在{@link com.chengdu.jiq.service.factorybean.FactoryBeanConfig} 中
     */
    @Resource
    private HttpClient httpClient;

    @Test
    public void testHttpClient() {
        System.out.print(httpClient.sendRequest());
    }
}
