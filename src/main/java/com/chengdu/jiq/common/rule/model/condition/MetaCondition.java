package com.chengdu.jiq.common.rule.model.condition;

import com.chengdu.jiq.common.rule.model.enums.CompareMethod;

import java.util.List;

/**
 * Created by jiyiqin on 2018/5/16.
 */
public class MetaCondition {
    private String left;
    private CompareMethod compareMethod;
    private List<Object> rights;

    public MetaCondition(String left,
                         CompareMethod compareMethod,
                         List<Object> rights) {
        this.left = left;
        this.compareMethod = compareMethod;
        this.rights = rights;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public CompareMethod getCompareMethod() {
        return compareMethod;
    }

    public void setCompareMethod(CompareMethod compareMethod) {
        this.compareMethod = compareMethod;
    }

    public List<Object> getRights() {
        return rights;
    }

    public void setRights(List<Object> rights) {
        this.rights = rights;
    }
}
