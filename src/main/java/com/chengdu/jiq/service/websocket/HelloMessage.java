package com.chengdu.jiq.service.websocket;

/**
 * Created by jiyiqin on 2018/4/22.
 */
public class HelloMessage {

    private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
