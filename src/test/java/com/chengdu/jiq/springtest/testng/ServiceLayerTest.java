package com.chengdu.jiq.springtest.testng;

import com.chengdu.jiq.model.bo.CityModel;
import com.chengdu.jiq.service.async.service.AsyncTaskService;
import com.chengdu.jiq.service.jdbc.service.CityService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by jiyiqin on 2017/9/19.
 *
 * @ContextConfiguration defines class-level metadata that is used to determine how to load and configure
 * an ApplicationContext for integration tests. Specifically, @ContextConfiguration declares the application
 * context resource locations or the annotated classes that will be used to load the context.
 * @WebAppConfiguration is a class-level annotation that is used to declare that the ApplicationContext
 * loaded for an integration test should be a WebApplicationContext.
 * @TestPropertySource is a class-level annotation that is used to configure the locations
 * of properties files and inlined properties to be added to the set of PropertySources in
 * the Environment for an ApplicationContext loaded for an integration test.
 * @Test 注解需要引自testng包，否则报错： Error:(35, 8) java: 无法访问org.testng.IHookable找不到org.testng.IHookable的类文件
 */

//@RunWith(SpringRunner.class) //不需要,使用AbstractTestNGSpringContextTests
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
@TestPropertySource("/application.properties")
public class ServiceLayerTest extends AbstractTestNGSpringContextTests {

    @Resource
    private AsyncTaskService asyncTaskService;

    @Autowired
    private CityService cityService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeClass(dependsOnMethods = {"springTestContextPrepareTestInstance"})
    private void beforeClass() {
        cleanTestData();
        String insertSql = "insert into city(`ID`,\n" +
                "`Name`,\n" +
                "`CountryCode`,\n" +
                "`District`,\n" +
                "`Population`) values(?,?,?,?,?)";
        jdbcTemplate.update(insertSql, 100000, "jiyiqin", "A", "jiyiqin", 12345678);
    }

    @AfterClass
    private void afterClass() {
        //can not delete?
        cleanTestData();
    }

    private void cleanTestData() {
        String deleteSql = "delete from city where id = ?";
        jdbcTemplate.update(deleteSql, 100000);
    }

    @BeforeMethod
    private void beforeMethod() {
    }

    @AfterMethod
    private void afterMethod() {
    }

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

    @Test
    public void testDbOperation2() {
        CityModel model = cityService.findByJdbcTemplate(100000L);
        Assert.assertNotNull(model);
    }
}
