package com.chengdu.jiq.springtest.junit;

import com.chengdu.jiq.SpringbootDevServiceApplication;
import com.chengdu.jiq.model.bo.CityModel;
import com.chengdu.jiq.service.async.service.AsyncTaskService;
import com.chengdu.jiq.service.jdbc.service.CityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by jiyiqin on 2017/9/19.
 *
 * @ContextConfiguration defines class-level metadata that is used to determine how to load and configure
 * an ApplicationContext for integration tests. Specifically, @ContextConfiguration declares the application
 * context resource locations or the annotated classes that will be used to load the context.
 *
 * @WebAppConfiguration is a class-level annotation that is used to declare that the ApplicationContext
 * loaded for an integration test should be a WebApplicationContext.
 *
 * @TestPropertySource is a class-level annotation that is used to configure the locations
 * of properties files and inlined properties to be added to the set of PropertySources in
 * the Environment for an ApplicationContext loaded for an integration test.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringbootDevServiceApplication.class})
@TestPropertySource("/application.properties")
@WebAppConfiguration
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
