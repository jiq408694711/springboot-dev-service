package com.chengdu.jiq.springtest.junit.forkjoin;

import com.chengdu.jiq.SpringbootDevServiceApplication;
import com.chengdu.jiq.service.forkjoin.ForkJoinConfig;
import com.chengdu.jiq.service.forkjoin.ForkJoinService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by jiyiqin on 2017/11/19.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringbootDevServiceApplication.class})
@TestPropertySource("/application.properties")
@WebAppConfiguration
public class ForkJoinTest {

    @Autowired
    private ForkJoinService forkJoinService;

    @Test
    public void testForkJoin() throws IOException {
        String inputPath = "E:\\Code\\springboot-dev-service\\target\\classes\\img\\kg.jpg";
        String outputPath = "E:\\Code\\springboot-dev-service\\target\\classes\\img\\kg_out.jpg";
        forkJoinService.blurImage(inputPath, outputPath);
    }

    @Test
    public void testForkJoin2() throws IOException, ExecutionException, InterruptedException {
        System.out.println("########################" + forkJoinService.sum(1L, 10000L));
    }
}
