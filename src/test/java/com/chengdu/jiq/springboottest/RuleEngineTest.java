package com.chengdu.jiq.springboottest;

import com.chengdu.jiq.common.rule.engine.DrRuleEngine;
import com.chengdu.jiq.common.rule.model.DrCondition;
import com.chengdu.jiq.common.rule.model.DrRule;
import com.chengdu.jiq.common.rule.model.MetaCondition;
import com.chengdu.jiq.common.rule.model.enums.CompareMethod;
import com.chengdu.jiq.common.rule.model.enums.ConditionType;
import com.chengdu.jiq.service.rules.SendAwardAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiyiqin on 2017/9/19.
 * <p>
 * * 关于SpringBootTest注解：
 * The @SpringBootTest annotation tells Spring Boot to go and look for a main
 * configuration class (one with @SpringBootApplication for instance), and use
 * that to start a Spring application context.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleEngineTest {

    @Resource
    private DrRuleEngine drRuleEngine;

    @Test
    public void testGeneralData() throws Exception {
        /**
         * 构造规则
         */
        DrRule rule = new DrRule();
        DrCondition drCondition1 = new DrCondition();
        drCondition1.setType(ConditionType.GENERAL);
        drCondition1.setMetaCondition(new MetaCondition("investAmount", false, CompareMethod.GRATER, Arrays.asList(10000)));
        rule.setConditions(Arrays.asList(drCondition1));
        rule.setActions(Arrays.asList(new SendAwardAction("award1"), new SendAwardAction("award2")));

        /**
         * 准备数据
         */
        Map<String, Object> data = new HashMap<>();
        data.put("investAmount", 50000);
        data.put("cellPhone", "18190800520");

        /**
         * 运行规则
         */
        drRuleEngine.runRuleEngine(data, Arrays.asList(rule));
    }
}
