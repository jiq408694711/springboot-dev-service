package com.chengdu.jiq.common.rule.model;

import java.util.List;

/**
 * Created by jiyiqin on 2018/5/16.
 */
public class DrRule {
    private Long id;
    private List<DrCondition> conditions;
    private List<DrAction> actions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DrCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<DrCondition> conditions) {
        this.conditions = conditions;
    }

    public List<DrAction> getActions() {
        return actions;
    }

    public void setActions(List<DrAction> actions) {
        this.actions = actions;
    }
}
