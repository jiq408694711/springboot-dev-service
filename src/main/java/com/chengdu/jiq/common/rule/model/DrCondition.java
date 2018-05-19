package com.chengdu.jiq.common.rule.model;

import com.chengdu.jiq.common.rule.model.stream.Duration;
import com.chengdu.jiq.common.rule.model.enums.ConditionType;
import com.chengdu.jiq.common.rule.model.enums.ReduceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiyiqin on 2018/5/16.
 */
public class DrCondition {
    private ConditionType type;

    /**
     * 基础数据规则
     */
    private MetaCondition metaCondition;

    /**
     * 流式数据规则
     */
    private String streamKey;
    private Duration duration;
    private List<MetaCondition> metaConditions = new ArrayList();
    private ReduceType reduce;

    public DrCondition(ConditionType type, MetaCondition metaCondition) {
        this.type = type;
        this.metaCondition = metaCondition;
    }

    public ConditionType getType() {
        return type;
    }

    public void setType(ConditionType type) {
        this.type = type;
    }

    public MetaCondition getMetaCondition() {
        return metaCondition;
    }

    public void setMetaCondition(MetaCondition metaCondition) {
        this.metaCondition = metaCondition;
    }

    public String getStreamKey() {
        return streamKey;
    }

    public void setStreamKey(String streamKey) {
        this.streamKey = streamKey;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public List<MetaCondition> getMetaConditions() {
        return metaConditions;
    }

    public void setMetaConditions(List<MetaCondition> metaConditions) {
        this.metaConditions = metaConditions;
    }

    public ReduceType getReduce() {
        return reduce;
    }

    public void setReduce(ReduceType reduce) {
        this.reduce = reduce;
    }
}
