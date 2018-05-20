package com.chengdu.jiq.common.rule.model.stream;

import com.chengdu.jiq.common.rule.model.enums.DurationType;

/**
 * Created by jiyiqin on 2018/5/16.
 */
public class Duration {
    private DurationType type;
    private Integer value;
    private Long beginTime;
    private Long endTime;

    public Duration(DurationType type, Integer value) {
        this.type = type;
        this.value = value;
    }

    public DurationType getType() {
        return type;
    }

    public void setType(DurationType type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
