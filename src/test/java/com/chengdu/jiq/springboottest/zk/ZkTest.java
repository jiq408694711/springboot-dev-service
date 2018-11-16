package com.chengdu.jiq.springboottest.zk;

import com.chengdu.jiq.service.zk.ZkToolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created by jiyiqin on 2017/9/19.
 * <p>
 * * 关于SpringBootTest注解：
 * The @SpringBootTest annotation tells Spring Boot to go and look for a main
 * configuration class (one with @SpringBootApplication for instance), and use
 * that to start a Spring application context.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZkTest {

    @Resource
    private ZkToolService zkToolService;

    @Test
    public void test() throws Exception {
        zkToolService.parse();
    }

}
