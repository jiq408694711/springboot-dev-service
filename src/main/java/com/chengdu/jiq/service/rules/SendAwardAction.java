package com.chengdu.jiq.service.rules;

import com.chengdu.jiq.common.rule.model.DrAction;

/**
 * Created by jiyiqin on 2018/5/17.
 */
public class SendAwardAction extends DrAction {
    private String awardName;
    public SendAwardAction(String awardName) {
        this.awardName = awardName;
    }

    @Override
    public void action() {
        System.out.println("发放奖励:" + awardName);
    }
}
