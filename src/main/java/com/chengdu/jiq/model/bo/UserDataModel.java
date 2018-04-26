package com.chengdu.jiq.model.bo;

import java.math.BigDecimal;

/**
 * Created by jiyiqin on 2018/3/28.
 */
public class UserDataModel {
    private String name;
    private BigDecimal investAmount;
    private Boolean firstInvest;

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
}
