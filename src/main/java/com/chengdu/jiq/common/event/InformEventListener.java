package com.chengdu.jiq.common.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by jiyiqin on 2017/11/18.
 */
@Component
public class InformEventListener implements ApplicationListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(InformEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        LOGGER.info("事件处理Listener被触发");
        if(applicationEvent instanceof InformEvent) {
            LOGGER.info("收到消息通知: {}", ((InformEvent)applicationEvent).getInformMsg());
        }
    }
}
