package com.chengdu.jiq.service.rules;

import com.chengdu.jiq.model.bo.InvestMessageModel;
import com.chengdu.jiq.model.bo.UserDataModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jiyiqin on 2018/3/30.
 */

@Service
public class RuleService {

    public UserDataModel initConditionData(UserDataModel userDataModel, String conditionName) {
        System.out.println("获取条件数据:" + conditionName);
        userDataModel.getContext().put("totalInvestAmount", 1000000);
        return userDataModel;
    }

    public Map<String, Object> initConditionData2(Map<String, Object> data, String... conditionNames) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("ruleService:" + data);
        data.put("investTotalCount", 5000);
        data.put("investAmount", 4000);
        return data;
    }

    public List<InvestMessageModel> selectInvestRecords(UserDataModel userDataModel) {
        List<InvestMessageModel> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            InvestMessageModel model = new InvestMessageModel();
            model.setActorId("123");
            model.setInvestAmount(new BigDecimal("3000"));
            model.setInvestTime(new Date());
            list.add(model);
        }
        return list;
    }

    public void hello() {
        System.out.println("service called");
    }

    public void initContext(List<String> keys) {
        System.out.println("当前规则需要初始化的条件包括：" + keys);
    }
}
