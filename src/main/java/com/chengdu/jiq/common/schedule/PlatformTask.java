package com.chengdu.jiq.common.schedule;

import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by jiyiqin on 2017/11/19.
 */
public abstract class PlatformTask<T> implements Runnable {

    private T model;

    private ScheduledFuture future;

    public PlatformTask(T model) {
        this.model = model;
    }

    @Override
    public void run() {
        execute(model);
    }

    protected abstract boolean expire(String identity);

    protected abstract void execute(T module);

    protected abstract String identity(T module);

    protected abstract CronTrigger cronTrigger(T module);

    public boolean isExpire() {
        return expire(identity(model));
    }

    public String getIdentity() {
        return identity(model);
    }

    public CronTrigger getCronTrigger() {
        return cronTrigger(model);
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public ScheduledFuture getFuture() {
        return future;
    }

    public void setFuture(ScheduledFuture future) {
        this.future = future;
    }
}
