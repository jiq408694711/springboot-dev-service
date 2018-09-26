package com.chengdu.jiq.springboottest.sharding;

import com.chengdu.jiq.model.bo.OrderModel;
import com.chengdu.jiq.service.shardingjdbc.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
public class OrderTest {

    @Resource
    private OrderService orderService;

    @Test
    public void test() throws Exception {
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            OrderModel model = new OrderModel();
            //全局唯一主键id自动生成
            model.setOrderId(Long.parseLong(String.valueOf(r.nextInt(10000))));
            model.setUserId(1231221L);
            Assert.assertTrue(orderService.insert(model) == 1);
        }
    }
}
