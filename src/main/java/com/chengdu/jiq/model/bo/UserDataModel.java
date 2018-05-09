package com.chengdu.jiq.model.bo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiyiqin on 2018/3/28.
 */
public class UserDataModel {
    private String name;
    private BigDecimal investAmount;
    private Boolean firstInvest;
    private Map<String, Object> context = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(BigDecimal investAmount) {
        this.investAmount = investAmount;
    }

    public Boolean getFirstInvest() {
        return firstInvest;
    }

    public void setFirstInvest(Boolean firstInvest) {
        this.firstInvest = firstInvest;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }
}
