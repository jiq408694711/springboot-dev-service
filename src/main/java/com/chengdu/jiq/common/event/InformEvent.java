package com.chengdu.jiq.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by jiyiqin on 2017/11/18.
 */
public class InformEvent extends ApplicationEvent {

    private String informMsg;

    public InformEvent(String informMsg) {
        super(informMsg);
        this.informMsg = informMsg;
    }

    public String getInformMsg() {
        return informMsg;
    }

    public void setInformMsg(String informMsg) {
        this.informMsg = informMsg;
    }
}
