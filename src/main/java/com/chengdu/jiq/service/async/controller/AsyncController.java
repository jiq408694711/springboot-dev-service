package com.chengdu.jiq.service.async.controller;

import com.chengdu.jiq.common.annotation.ControllerAspect;
import com.chengdu.jiq.model.vo.ResponseData;
import com.chengdu.jiq.service.async.service.AsyncTaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * Created by jiyiqin on 2017/9/16.
 */
@ControllerAspect
@RestController
@RequestMapping("/async")
public class AsyncController {

    @Resource
    private AsyncTaskService asyncTaskService;

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public ResponseData task() throws Exception {
        long start = System.currentTimeMillis();

        Future<String> task1 = asyncTaskService.doTaskOne();
        Future<String> task2 = asyncTaskService.doTaskTwo();
        Future<String> task3 = asyncTaskService.doTaskThree();

        while (true) {
            if (task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }

        long end = System.currentTimeMillis();

        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
        return ResponseData.ok(end - start);
    }
}
