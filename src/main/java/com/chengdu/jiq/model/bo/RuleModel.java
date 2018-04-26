package com.chengdu.jiq.model.bo;

/**
 * Created by jiyiqin on 2018/4/26.
 */
public class RuleModel {
    private String conditions;
    private RuleCheckResult result;

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public RuleCheckResult getResult() {
        return result;
    }

    public void setResult(RuleCheckResult result) {
        this.result = result;
    }
}
