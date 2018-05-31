package com.chengdu.jiq.service.rules.service;

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

    public List<Map<String, Object>> selectInvestRecords(Map<String, Object> data) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("actorId", "123");
            map.put("investAmount", new BigDecimal("3000.5"));
            map.put("investTime", new Date());
            list.add(map);
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
