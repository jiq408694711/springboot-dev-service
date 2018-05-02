package com.chengdu.jiq.model.bo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiyiqin on 2018/4/26.
 */
public class RuleModel {
    private Long id;
    private String conditions;
    private String type;    //轮训、概率随机、所有等
    private List<AwardModel> awards = new ArrayList();
    private boolean result = false; // true:通过校验；false：未通过校验

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AwardModel> getAwards() {
        return awards;
    }

    public void setAwards(List<AwardModel> awards) {
        this.awards = awards;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
