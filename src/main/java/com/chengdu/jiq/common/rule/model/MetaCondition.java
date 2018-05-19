package com.chengdu.jiq.common.rule.model;

import com.chengdu.jiq.common.rule.model.enums.CompareMethod;

import java.util.List;

/**
 * Created by jiyiqin on 2018/5/16.
 */
public class MetaCondition {
    private String dataKey;
    private CompareMethod compareMethod;
    private List<Object> compareValues;

    public MetaCondition(String dataKey,
                         CompareMethod compareMethod,
                         List<Object> compareValues) {
        this.dataKey = dataKey;
        this.compareMethod = compareMethod;
        this.compareValues = compareValues;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
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
