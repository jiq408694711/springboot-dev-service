package com.chengdu.jiq.common.rule.model;

import com.chengdu.jiq.common.rule.model.enums.CompareMethod;
import com.chengdu.jiq.common.rule.model.enums.ConditionType;
import com.chengdu.jiq.common.rule.model.enums.ReduceType;
import com.chengdu.jiq.common.rule.model.stream.Duration;

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
    private String reduceKey;
    private CompareMethod compareMethod;
    private List<Object> compareValues;

    public static DrCondition newGeneralCondition(MetaCondition metaCondition) {
        DrCondition drCondition = new DrCondition();
        drCondition.setType(ConditionType.GENERAL);
        drCondition.setMetaCondition(metaCondition);
        return drCondition;
    }

    public static DrCondition newStreamCondition(String streamKey,
                                                 Duration duration,
                                                 List<MetaCondition> metaConditions,
                                                 ReduceType reduce,
                                                 String reduceKey,
                                                 CompareMethod compareMethod,
                                                 List<Object> compareValues) {
        DrCondition drCondition = new DrCondition();
        drCondition.setType(ConditionType.STREAM);
        drCondition.setStreamKey(streamKey);
        drCondition.setDuration(duration);
        drCondition.setMetaConditions(metaConditions);
        drCondition.setReduce(reduce);
        drCondition.setReduceKey(reduceKey);
        drCondition.setCompareMethod(compareMethod);
        drCondition.setCompareValues(compareValues);
        return drCondition;
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
