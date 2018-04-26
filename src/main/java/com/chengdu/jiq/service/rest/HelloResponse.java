package com.chengdu.jiq.service.rest;

import java.util.Date;

/**
 * Created by jiyiqin on 2017/11/22.
 */
public class HelloResponse {

    private String name;
    private Date time;
    private Long age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
