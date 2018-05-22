package com.chengdu.jiq.common.rule.model.condition;

import com.chengdu.jiq.common.rule.model.DrCondition;
import com.chengdu.jiq.common.rule.model.enums.CompareMethod;
import com.chengdu.jiq.common.rule.model.enums.ReduceType;
import com.chengdu.jiq.common.rule.model.stream.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiyiqin on 2018/5/22.
 * 流式数据规则
 */
public class StreamCondition extends DrCondition {
    private String streamKey;
    private Duration duration;
    private List<MetaCondition> metaConditions = new ArrayList();
    private ReduceType reduceOp;
    private String reduceKey;
    private CompareMethod compareMethod;
    private List<Object> compareValues;

    public static StreamCondition newStreamCondition(String streamKey,
                                                     Duration duration,
                                                     List<MetaCondition> metaConditions,
                                                     ReduceType reduceOp,
                                                     String reduceKey,
                                                     CompareMethod compareMethod,
                                                     List<Object> compareValues) {
        StreamCondition drCondition = new StreamCondition();
        drCondition.setStreamKey(streamKey);
        drCondition.setDuration(duration);
        drCondition.setMetaConditions(metaConditions);
        drCondition.setReduceOp(reduceOp);
        drCondition.setReduceKey(reduceKey);
        drCondition.setCompareMethod(compareMethod);
        drCondition.setCompareValues(compareValues);
        return drCondition;
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

    public ReduceType getReduceOp() {
        return reduceOp;
    }

    public void setReduceOp(ReduceType reduceOp) {
        this.reduceOp = reduceOp;
    }

    public String getReduceKey() {
        return reduceKey;
    }

    public void setReduceKey(String reduceKey) {
        this.reduceKey = reduceKey;
    }

    public CompareMethod getCompareMethod() {
        return compareMethod;
    }

    public void setCompareMethod(CompareMethod compareMethod) {
        this.compareMethod = compareMethod;
    }

    public List<Object> getCompareValues() {
        return compareValues;
    }

    public void setCompareValues(List<Object> compareValues) {
        this.compareValues = compareValues;
    }
}
