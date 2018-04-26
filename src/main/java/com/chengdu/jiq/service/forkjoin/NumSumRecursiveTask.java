package com.chengdu.jiq.service.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * Created by jiyiqin on 2017/11/19.
 */
public class NumSumRecursiveTask extends RecursiveTask<Long> {
    private static final Long THRESHOLD = 100L;
    private Long start;
    private Long end;

    public NumSumRecursiveTask(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        Long sum = 0L;
        if ((end - start) < THRESHOLD) {
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            Long middle = (start + end) / 2;
            NumSumRecursiveTask leftTask = new NumSumRecursiveTask(start, middle);
            NumSumRecursiveTask rightTask = new NumSumRecursiveTask(middle + 1, end);
            leftTask.fork();
            rightTask.fork();
            sum = leftTask.join() + rightTask.join();
        }
        return sum;
    }
}
