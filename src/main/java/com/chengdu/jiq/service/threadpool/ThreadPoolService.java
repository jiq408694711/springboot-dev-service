package com.chengdu.jiq.service.threadpool;

import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * Created by jiyiqin on 2017/11/19.
 *
 * 我们都知道jdk中线程池实现主要是: {@link java.util.concurrent.ThreadPoolExecutor}
 * 是 {@link java.util.concurrent.ExecutorService} 接口的实现类。
 * 工具类{@link java.util.concurrent.Executors}的newFixedThreadPool,newCachedThreadPool等接口
 * 都是返回的ThreadPoolExecutor线程池。
 *
 * 那么Spring中为什么还要重新实现一个:{@link org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor}?
 * 原因很简单，JDK的ThreadPoolExecutor线程池的corePoolSize", "maxPoolSize", "keepAliveSeconds等方法没有set方法。
 * 而ThreadPoolTaskExecutor是其简单封装，提供了"corePoolSize", "maxPoolSize", "keepAliveSeconds"等属性的set方法，方便配置。
 *
 */
@Service
public class ThreadPoolService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolService.class);

    @Autowired
    private ExecutorService singleThreadExecutor;

    @Autowired
    private ExecutorService fixedThreadPool;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 测试JDK的ThreadPoolExecutor
     */
    public void testThreadPoolExecutor() {
        Future future = singleThreadExecutor.submit(newRunnable());
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Future future2 = fixedThreadPool.submit(newCallable());
        try {
            Integer result = (Integer) future2.get();
            LOGGER.info("测试JDK的ThreadPoolExecutor，返回结果: {}", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试Spring的ThreadPoolTaskExecutor
     */
    public void testThreadPoolTaskExecutor() {
        Future future = threadPoolTaskExecutor.submit(newRunnable());
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Future future2 = threadPoolTaskExecutor.submit(newCallable());
        try {
            Integer result = (Integer) future2.get();
            LOGGER.info("测试Spring的ThreadPoolTaskExecutor，返回结果: {}", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Runnable newRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                LOGGER.info("执行任务");
            }
        };
    }

    private Callable newCallable() {
        return new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                LOGGER.info("执行任务");
                return 10;
            }
        };
    }
}
