package com.chengdu.jiq.common.rule.model;

import java.util.List;
import java.util.UUID;

/**
 * Created by jiyiqin on 2018/5/16.
 */
public class DrRule {
    private String id = UUID.randomUUID().toString();
    private List<List<DrCondition>> conditions;
    private List<DrAction> actions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<List<DrCondition>> getConditions() {
        return conditions;
    }

    public void setConditions(List<List<DrCondition>> conditions) {
        this.conditions = conditions;
    }

    public List<DrAction> getActions() {
        return actions;
    }

    public void setActions(List<DrAction> actions) {
        this.actions = actions;
    }
}
