package com.chengdu.jiq.springboottest;

import com.chengdu.jiq.model.bo.CityModel;
import com.chengdu.jiq.service.async.service.AsyncTaskService;
import com.chengdu.jiq.service.jdbc.service.CityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

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
public class ServiceLayerTest {

    @Resource
    private AsyncTaskService asyncTaskService;

    @Autowired
    private CityService cityService;

    @Test
    public void testAsyncTask() throws Exception {
        Future<String> t1 = asyncTaskService.doTaskOne();
        while (true) {
            if (t1.isDone()) {
                break;
            }
            Thread.sleep(1000);
        }
        System.out.println("Task return");
    }

    @Test
    public void testDbOperation() {
        List<CityModel> list = cityService.listByJdbcTemplate();
        Assert.assertNotNull(list);
    }
}
