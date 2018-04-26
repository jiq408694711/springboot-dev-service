package com.chengdu.jiq.service.eventpublish.service;

import com.chengdu.jiq.common.event.InformEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Created by jiyiqin on 2017/11/18.
 */
@Service
public class PublishEventService implements ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public boolean publish(String eventParam) {
        applicationContext.publishEvent(new InformEvent(eventParam));
        return true;
    }
}
