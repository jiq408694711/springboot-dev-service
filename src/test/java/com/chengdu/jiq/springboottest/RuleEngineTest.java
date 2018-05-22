package com.chengdu.jiq.springboottest;

import com.chengdu.jiq.common.rule.engine.DrRuleEngine;
import com.chengdu.jiq.common.rule.model.DrCondition;
import com.chengdu.jiq.common.rule.model.DrRule;
import com.chengdu.jiq.common.rule.model.condition.MetaCondition;
import com.chengdu.jiq.common.rule.model.enums.CompareMethod;
import com.chengdu.jiq.common.rule.model.enums.DurationType;
import com.chengdu.jiq.common.rule.model.enums.ReduceType;
import com.chengdu.jiq.common.rule.model.stream.Duration;
import com.chengdu.jiq.service.rules.SendAwardAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    public void testBasicRule() throws Exception {

        /**
         * 准备数据
         */
        Map<String, Object> data = new HashMap<>();
        data.put("userId", 1110011);
        data.put("actionType", "INVEST");
        data.put("investAmount", 50000);
        data.put("investPlan", "33222");
        data.put("cellPhone", "18190800520");
        data.put("level", 4);
        data.put("investCount", 30);
//        data.put("registerTime", "2018-03-01 00:00:00");
//        data.put("realName", true);   //data中找不到${key}，则规则引擎会自动尝试通过initializer加载
//        data.put("inviteeRegisterCount", 8);

        /**
         * 构造规则
         */
        DrRule rule = new DrRule("testRuleId1");
        DrCondition drCondition1 = DrCondition.newGeneralCondition(new MetaCondition("${actionType}", CompareMethod.EQUAL, Arrays.asList("INVEST")));
        DrCondition drCondition2 = DrCondition.newGeneralCondition(new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(10000)));
        DrCondition drCondition3 = DrCondition.newGeneralCondition(new MetaCondition("${investPlan}", CompareMethod.IN, Arrays.asList("33221", "33222", "11891")));
//        DrCondition drCondition4 = new DrCondition(ConditionType.GENERAL, new MetaCondition("${cellPhone}", CompareMethod.STR_END_WITH, Arrays.asList("520")));

        //java表达式支持
        DrCondition drCondition5 = DrCondition.newGeneralCondition(new MetaCondition("${investAmount} * ${level}", CompareMethod.GRATER, Arrays.asList(30000)));
        DrCondition drCondition6 = DrCondition.newGeneralCondition(new MetaCondition("${level} * 5", CompareMethod.LESS_AND_EQUAL, Arrays.asList("${investCount}")));

        //外部数据支持
        DrCondition drCondition7 = DrCondition.newGeneralCondition(new MetaCondition("${registerTime}", CompareMethod.BETWEEN, Arrays.asList("2018-01-01 00:00:00", "2018-06-01 00:00:00")));
        DrCondition drCondition8 = DrCondition.newGeneralCondition(new MetaCondition("${realName}", CompareMethod.EQUAL, Arrays.asList(true)));
        DrCondition drCondition9 = DrCondition.newGeneralCondition(new MetaCondition("${inviteeRegisterCount} % 2", CompareMethod.EQUAL, Arrays.asList(0)));

        //流式数据支持
        MetaCondition metaCondition1 = new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(10000));
        DrCondition drCondition10 = DrCondition.newStreamCondition("invest_stream", null, Arrays.asList(metaCondition1), ReduceType.COUNT, null, CompareMethod.GRATER, Arrays.asList(3));
        DrCondition drCondition11 = DrCondition.newStreamCondition("invest_stream", null, null, ReduceType.SUM, "${InvestAmount}", CompareMethod.GRATER, Arrays.asList(50000));

        //带时间限制的流式数据支持
        MetaCondition metaCondition2 = new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(10000));
        DrCondition drCondition12 = DrCondition.newStreamCondition("STREAM_INVEST", new Duration(DurationType.LAST_DAYS, 7), Arrays.asList(metaCondition2), ReduceType.COUNT, null, CompareMethod.GRATER, Arrays.asList(3));

        //设置action
        rule.setConditions(Arrays.asList(Arrays.asList(drCondition1, drCondition2, drCondition3, drCondition5, drCondition6, drCondition7, drCondition8, drCondition9, drCondition10, drCondition11, drCondition12)));
        rule.setActions(Arrays.asList(new SendAwardAction("award1"), new SendAwardAction("award2")));

        /**
         * 运行规则
         */
        drRuleEngine.runRuleEngine(data, Arrays.asList(rule));
    }

    @Test
    public void testMultiRule() throws Exception {

        /**
         * 准备数据
         */
        Map<String, Object> data = new HashMap<>();
        data.put("userId", 1110011);
        data.put("actionType", "INVEST");
        data.put("investAmount", 50000);
        data.put("level", 4);
        data.put("investCount", 30);

        /**
         * 构造规则
         */
        DrRule rule = new DrRule("testRuleId2");

        //规则集合1
        DrCondition drCondition1 = DrCondition.newGeneralCondition(new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(50000)));
        DrCondition drCondition2 = DrCondition.newGeneralCondition(new MetaCondition("${riskEvaluation}", CompareMethod.EQUAL, Arrays.asList(true)));
        List<DrCondition> conditionGroup1 = Arrays.asList(drCondition1, drCondition2);

        //规则集合2
        DrCondition drCondition3 = DrCondition.newStreamCondition("STREAM_INVEST", new Duration(DurationType.LAST_DAYS, 7), null, ReduceType.COUNT, null, CompareMethod.GRATER, Arrays.asList(5));
        DrCondition drCondition4 = DrCondition.newStreamCondition("STREAM_INVEST", new Duration(DurationType.LAST_DAYS, 7), null, ReduceType.AVERAGE, "${InvestAmount}", CompareMethod.GRATER, Arrays.asList(1000));
        List<DrCondition> conditionGroup2 = Arrays.asList(drCondition3, drCondition4);

        //规则集合3

        DrCondition drCondition5 = DrCondition.newStreamCondition("STREAM_INVEST", new Duration(DurationType.NATURE_MONTH), null, ReduceType.COUNT, null, CompareMethod.GRATER, Arrays.asList(3));
        DrCondition drCondition6 = DrCondition.newStreamCondition("STREAM_COMMENTS", new Duration(DurationType.NATURE_MONTH), null, ReduceType.COUNT, null, CompareMethod.GRATER, Arrays.asList(10));
        List<DrCondition> conditionGroup3 = Arrays.asList(drCondition5, drCondition6);

        //设置action
        rule.setConditions(Arrays.asList(conditionGroup1, conditionGroup2, conditionGroup3));   //优先级按插入顺序，满足则跳出直接执行actions
        rule.setActions(Arrays.asList(new SendAwardAction("award1"), new SendAwardAction("award2")));

        /**
         * 运行规则
         */
        drRuleEngine.runRuleEngine(data, Arrays.asList(rule));
    }
}
