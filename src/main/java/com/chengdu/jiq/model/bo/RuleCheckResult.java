package com.chengdu.jiq.model.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiyiqin on 2018/3/28.
 */
public class RuleCheckResult {
    private boolean result = false; // true:通过校验；false：未通过校验
    private List<AwardModel> awards = new ArrayList();

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<AwardModel> getAwards() {
        return awards;
    }

    public void setAwards(List<AwardModel> awards) {
        this.awards = awards;
    }
}
