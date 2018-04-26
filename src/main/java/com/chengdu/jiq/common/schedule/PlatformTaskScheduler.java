package com.chengdu.jiq.common.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.util.ErrorHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by jiyiqin on 2017/11/18.
 * Spring3.0提供TaskScheduler接口用于任务的定时调度。
 * JDK中已经有了可调度线程池{@link java.util.concurrent.ScheduledThreadPoolExecutor}
 * 可以支持延迟执行任务，以及按照固定频率执行任务，为什么Spring还要搞一个TaskScheduler？
 * -- 支持更加强大的触发方式
 *
 * ### 调度器TaskScheduler的主要实现类有：
 * 1. {@link org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler}
 * 默认提供JDK中ScheduledThreadPoolExecutor可调度线程池实现，可设置线程池大小。
 * 2. {@link org.springframework.scheduling.concurrent.ConcurrentTaskScheduler}
 * 默认提供JDK中newSingleThreadScheduledExecutor返回的ScheduledExecutorService单线程实现，可传入自定义线程池
 *
 * ### 定时的信息由Trigger接口描述，主要包含两种Trigger：
 * 1. {@link org.springframework.scheduling.support.CronTrigger} 基于cron表达式
 * 2. {@link org.springframework.scheduling.support.PeriodicTrigger}固定周期，可选的延迟时间等
 *
 * 相比于@Schedule注解，TaskScheduler自定义调度器的好处是：
 * 1 可以动态增加调度任务
 * 2 可以随时取消调度任务
 *
 * 特别注意： 无论是TaskScheduler还是@Scheduled，调度的时间点都会被格式化为相同时间点
 * 比如有两个TaskScheduler启动的定时任务，每5分钟执行一次，那么无论何时启动，两个任务都是
 * 在13:05, 13:10, 13:15 ... 这样的时间点去执行，以自己机器的时钟为准，如果是两个每3秒钟
 * 执行的任务，那就是在13:05:03, 13:05:06, 13:05:09 .. 这样去执行，总之多个任务一定会被
 * 格式化到相同的时间点，这样有好处，比如争抢0-1信号量，保证每个时间单位内只有一个任务真实执行
 */
public abstract class PlatformTaskScheduler<T> implements InitializingBean, DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformTaskScheduler.class);

    //jdk线程池
    private ScheduledExecutorService scheduledExecutorService;

    //TaskScheduler
    private ConcurrentTaskScheduler taskScheduler;

    //正在运行中的任务
    private Map<String, PlatformTask<T>> runnerMap;

    @Override
    public void destroy() throws Exception {
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.shutdown();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        runnerMap = new LinkedHashMap<>();
        scheduledExecutorService = Executors.newScheduledThreadPool(10);
        taskScheduler = new ConcurrentTaskScheduler(scheduledExecutorService);
        taskScheduler.setErrorHandler(new ErrorHandler() {
            @Override
            public void handleError(Throwable throwable) {
                LOGGER.info("Task Scheduler Exception: {}", throwable);
            }
        });
    }

    /**
     * 此方法在子类中调用，因为业务不同刷新调度任务的频率也不相同
     */
//    @Scheduled(fixedRate = 1000 * 30 * 1)
    public void schedule() {
        List<PlatformTask<T>> tasks = this.getSchedulableTask();
        LOGGER.info("schedule");

        //判断正在运行的任务业务上是否有需要终止的
        runnerMap.entrySet().stream()
                .filter(entry -> entry.getValue().isExpire() && !entry.getValue().getFuture().isCancelled())
                .forEach(entry -> {
                    try {
                        LOGGER.info("任务:{}终止", entry.getKey());
                        entry.getValue().getFuture().cancel(false);
                        runnerMap.remove(entry.getKey());
                    } catch (Exception e) {
                        LOGGER.error("任务:{}终止失败:{}", entry.getKey(), e);
                    }
                });

        //调度任务执行
        tasks.stream()
                .filter(task -> !runnerMap.containsKey(task.getIdentity()) && !task.isExpire() && task.getCronTrigger() != null)
                .forEach(task -> {
                    try {
                        LOGGER.info("调度任务: {} cron表达式: {}", task.getIdentity(), task.getCronTrigger());
                        ScheduledFuture future = taskScheduler.schedule(task, task.getCronTrigger());
                        task.setFuture(future);
                        runnerMap.put(task.getIdentity(), task);
                    } catch (Exception e) {
                        LOGGER.error("调度任务{}失败:{}", task.getIdentity(), e);
                    }
                });
    }

    //获取需要调度的任务列表
    protected abstract <T> List<PlatformTask<T>> getSchedulableTask();
}
