package com.chengdu.jiq.service.rules.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jiyiqin on 2018/5/26.
 */
public class InvestMessage {
    private String aId;
    private BigDecimal investAmount;
    private Date createTime;

    public InvestMessage(BigDecimal investAmount) {
        this.setInvestAmount(investAmount);
    }

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public BigDecimal getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(BigDecimal investAmount) {
        this.investAmount = investAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
