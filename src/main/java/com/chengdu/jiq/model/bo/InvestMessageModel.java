package com.chengdu.jiq.model.bo;

import org.kie.api.definition.type.Role;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jiyiqin on 2018/3/28.
 */
@org.kie.api.definition.type.Role(Role.Type.EVENT)
//@org.kie.api.definition.type.Duration("durationAttr")
@org.kie.api.definition.type.TypeSafe(true)
@org.kie.api.definition.type.Timestamp("investTime")
@org.kie.api.definition.type.Expires("2h30m")
public class InvestMessageModel {
    private String actorId;
    private BigDecimal investAmount;
    private Date investTime;

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public BigDecimal getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(BigDecimal investAmount) {
        this.investAmount = investAmount;
    }

    public Date getInvestTime() {
        return investTime;
    }

    public void setInvestTime(Date investTime) {
        this.investTime = investTime;
    }
}
