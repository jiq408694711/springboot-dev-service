package com.chengdu.jiq.service.rules.model;

import java.util.Date;

/**
 * Created by jiyiqin on 2018/5/26.
 */
public class UserInfo {
    private String aId;
    private Date registerTime;

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}
