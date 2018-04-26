package com.chengdu.jiq.springtest.junit.thread;

import com.chengdu.jiq.SpringbootDevServiceApplication;
import com.chengdu.jiq.service.threadpool.ThreadPoolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by jiyiqin on 2017/11/19.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringbootDevServiceApplication.class})
@TestPropertySource("/application.properties")
@WebAppConfiguration
public class ThreadPoolTest {

    @Autowired
    private ThreadPoolService threadPoolService;

    @Test
    public void testThread() {
        threadPoolService.testThreadPoolExecutor();
        threadPoolService.testThreadPoolTaskExecutor();
    }
}
