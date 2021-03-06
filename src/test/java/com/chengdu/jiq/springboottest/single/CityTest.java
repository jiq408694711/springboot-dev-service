package com.chengdu.jiq.springboottest.single;

import com.chengdu.jiq.model.bo.CityModel;
import com.chengdu.jiq.model.bo.OrderModel;
import com.chengdu.jiq.service.jdbc.service.CityService;
import com.chengdu.jiq.service.shardingjdbc.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

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
public class CityTest {

    @Resource
    private CityService cityService;

    @Test
    public void test() throws Exception {
        List<CityModel> list = cityService.listByMybatis();
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void test2() throws Exception {
        List<CityModel> list = cityService.listByCountryCode("AFG");
        Assert.assertTrue(list.size() > 0);
    }
}
