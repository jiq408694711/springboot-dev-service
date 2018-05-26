package com.chengdu.jiq.springboottest;

import com.chengdu.jiq.common.rule.engine.DrRuleEngine;
import com.chengdu.jiq.common.rule.model.DrCondition;
import com.chengdu.jiq.common.rule.model.DrRule;
import com.chengdu.jiq.common.rule.model.condition.BaseCondition;
import com.chengdu.jiq.common.rule.model.condition.MetaCondition;
import com.chengdu.jiq.common.rule.model.condition.StreamCondition;
import com.chengdu.jiq.common.rule.model.enums.CompareMethod;
import com.chengdu.jiq.common.rule.model.enums.DurationType;
import com.chengdu.jiq.common.rule.model.enums.ReduceType;
import com.chengdu.jiq.common.rule.model.stream.Duration;
import com.chengdu.jiq.service.rules.SendAwardAction;
import org.apache.commons.lang3.time.DateUtils;
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
        //准备数据
        Map<String, Object> data = new HashMap<>();
        data.put("aId", 1110011);
        data.put("actionType", "INVEST");
        data.put("investAmount", 50000);
        data.put("investPlan", "33222");
        data.put("cellPhone", "18190800520");
        data.put("level", 4);
        //构造规则
        DrCondition d1 = BaseCondition.newBaseCondition(new MetaCondition("${actionType}", CompareMethod.EQUAL, Arrays.asList("INVEST")));
        DrCondition d2 = BaseCondition.newBaseCondition(new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(10000)));
        DrCondition d3 = BaseCondition.newBaseCondition(new MetaCondition("${investPlan}", CompareMethod.NOT_IN, Arrays.asList("33221", "33222")));
        DrCondition d4 = BaseCondition.newBaseCondition(new MetaCondition("${cellPhone}", CompareMethod.STR_STARTS_WITH, Arrays.asList("181")));
        //设置action
        DrRule rule = new DrRule("testRule1");
        rule.setConditions(Arrays.asList(Arrays.asList(d1, d2, d3, d4)));
        rule.setActions(Arrays.asList(new SendAwardAction("award1"), new SendAwardAction("award2")));
        //运行规则
        drRuleEngine.runRuleEngine(data, Arrays.asList(rule));
    }

    private List<DrCondition> generateRUles() {

        //(投资额为偶数、且投资额大于年龄的30倍和用户等级的100倍中最小的那个值)
        //任意java表达式支持
        DrCondition drCondition5 = BaseCondition.newBaseCondition(new MetaCondition("${investAmount} % 2", CompareMethod.EQUAL, Arrays.asList(0)));
        DrCondition drCondition6 = BaseCondition.newBaseCondition(new MetaCondition("${investAmount}", CompareMethod.GRATER,
                Arrays.asList("Math.min(${age} * 30, ${level} * 100)")));

        //(注册时间在指定范围内、已经完成实名、且在注册后的20天内完成绑卡
        //运行时计算并加载外部数据
        DrCondition drCondition7 = BaseCondition.newBaseCondition(new MetaCondition("${registerTime}", CompareMethod.BETWEEN,
                Arrays.asList("2018-01-01 00:00:00", "2018-06-01 00:00:00")));
        DrCondition drCondition8 = BaseCondition.newBaseCondition(new MetaCondition("${registerChannel}", CompareMethod.IN,
                Arrays.asList("channelA", "channelB")));
        DrCondition drCondition9 = BaseCondition.newBaseCondition(new MetaCondition("${bindCardTime}", CompareMethod.LESS_AND_EQUAL,
                Arrays.asList("DateUtils.addDays(${registerTime}, 20)")));

        //完成实名、或完成绑卡
        //"或"关系支持
        MetaCondition metaConditionA = new MetaCondition("${hasRealName}", CompareMethod.EQUAL, Arrays.asList(true));
        MetaCondition metaConditionB = new MetaCondition("${hasBindCard}", CompareMethod.EQUAL, Arrays.asList(true));
        DrCondition drCondition10 = BaseCondition.newBaseCondition(metaConditionA, metaConditionB);

        //(用户大于1w的投资的次数、大于3次)
        //流式数据支持
        MetaCondition filterCondition11 = new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(10000));
        StreamCondition.Reduce reduce1 = StreamCondition.newReduce("STREAM_INVEST", null, Arrays.asList(filterCondition11),
                ReduceType.COUNT, null);
        DrCondition drCondition11 = StreamCondition.newStreamCondition(reduce1, new MetaCondition("${reduceValue}", CompareMethod.GRATER,
                Arrays.asList(3)));

        //(用户大于1w的投资的总额、大于5w)
        //流式数据支持
        MetaCondition filterCondition21 = new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(10000));
        StreamCondition.Reduce reduce2 = StreamCondition.newReduce("STREAM_INVEST", null, Arrays.asList(filterCondition21),
                ReduceType.SUM, "${InvestAmount}");
        DrCondition drCondition12 = StreamCondition.newStreamCondition(reduce2, new MetaCondition("${reduceValue}", CompareMethod.GRATER,
                Arrays.asList(50000)));

        //最近7天投资额大于1w的总次数、大于3次
        //带时间限制的流式数据支持
        MetaCondition filterCondition31 = new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(10000));
        StreamCondition.Reduce reduce3 = StreamCondition.newReduce("STREAM_INVEST", new Duration(DurationType.LAST_DAYS, 7),
                Arrays.asList(filterCondition31), ReduceType.COUNT, null);
        DrCondition drCondition13 = StreamCondition.newStreamCondition(reduce3, new MetaCondition("${reduceValue}", CompareMethod.GRATER,
                Arrays.asList(3)));

        //存在发生在注册后20天内、且投资金额大于1w的首次投资
        //带时间限制的流式数据支持
        MetaCondition filterCondition41 = new MetaCondition("${investTime}", CompareMethod.LESS_AND_EQUAL,
                Arrays.asList("DateUtils.addDays(${registerTime}, 20)"));
        MetaCondition filterCondition42 = new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(10000));
        StreamCondition.Reduce reduce4 = StreamCondition.newReduce("STREAM_INVEST", new Duration(DurationType.AHEAD_LENGTH, 1),
                Arrays.asList(filterCondition41, filterCondition42), ReduceType.COUNT, null);
        DrCondition drCondition14 = StreamCondition.newStreamCondition(reduce4, new MetaCondition("${reduceValue}", CompareMethod.GRATER,
                Arrays.asList(0)));

        //当前自然周内存在投团范围在指定团范围内、且投资额大于1w的首次投资
        //带双重时间限制的流式数据支持
        MetaCondition filterCondition51 = new MetaCondition("${investPlan}", CompareMethod.IN, Arrays.asList("33221", "33222", "11891"));
        MetaCondition filterCondition52 = new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(10000));
        StreamCondition.Reduce reduce5 = StreamCondition.newReduce("STREAM_INVEST", new Duration(DurationType.NATURE_WEEK_AHEAD_LENGTH, 1),
                Arrays.asList(filterCondition51, filterCondition52), ReduceType.COUNT, null);
        DrCondition drCondition15 = StreamCondition.newStreamCondition(reduce5, new MetaCondition("${reduceValue}", CompareMethod.GRATER,
                Arrays.asList(0)));

        //用户邀请的人满足：从指定活动注册进来的、且是第奇数个完成首投大于1w的
        //带表达式的流式数据支持
        MetaCondition filterCondition61 = new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(10000));
        MetaCondition filterCondition62 = new MetaCondition("${registerActivityId}", CompareMethod.EQUAL, Arrays.asList(32192));
        StreamCondition.Reduce reduce6 = StreamCondition.newReduce("STREAM_REFEREE_FIRST_INVEST", null,
                Arrays.asList(filterCondition61, filterCondition62), ReduceType.COUNT, null);
        DrCondition drCondition16 = StreamCondition.newStreamCondition(reduce6, new MetaCondition("${reduceValue} % 2", CompareMethod.EQUAL,
                Arrays.asList(1)));

        //用户邀请的人满足：从指定活动注册进来的、且第1、3、5、7个完成首投大于1w的
        //带表达式的流式数据支持
        MetaCondition filterCondition71 = new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(10000));
        MetaCondition filterCondition72 = new MetaCondition("${registerActivityId}", CompareMethod.EQUAL, Arrays.asList(32192));
        StreamCondition.Reduce reduce7 = StreamCondition.newReduce("STREAM_REFEREE_FIRST_INVEST", null,
                Arrays.asList(filterCondition71, filterCondition72), ReduceType.COUNT, null);
        DrCondition drCondition17 = StreamCondition.newStreamCondition(reduce7, new MetaCondition("${reduceValue}", CompareMethod.IN,
                Arrays.asList(1, 3, 5, 7)));

        return Arrays.asList(drCondition5, drCondition6, drCondition7, drCondition8, drCondition9, drCondition10, drCondition11, drCondition12, drCondition13, drCondition14, drCondition15, drCondition16, drCondition17);
    }


    @Test
    public void testExternalData() throws Exception {
        //准备数据
        Map<String, Object> data = new HashMap<>();
        data.put("aId", 1110011);
        data.put("actionType", "INVEST");
        data.put("investAmount", 50000);
        data.put("investPlan", "33222");
        data.put("cellPhone", "18190800520");
        data.put("level", 4);

        MetaCondition filterCondition31 = new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(1000));
        StreamCondition.Reduce reduce3 = StreamCondition.newReduce("STREAM_INVEST", new Duration(DurationType.LAST_DAYS, 7),
                Arrays.asList(filterCondition31), ReduceType.COUNT, null);
        DrCondition drCondition13 = StreamCondition.newStreamCondition(reduce3, new MetaCondition("${reduceValue}", CompareMethod.GRATER,
                Arrays.asList(3)));

        //设置action
        DrRule rule = new DrRule("testRule1");
        rule.setConditions(Arrays.asList(Arrays.asList(drCondition13)));
        rule.setActions(Arrays.asList(new SendAwardAction("award1"), new SendAwardAction("award2")));
        //运行规则
        drRuleEngine.runRuleEngine(data, Arrays.asList(rule));
    }

    @Test
    public void testStreamlData() throws Exception {
        //准备数据
        Map<String, Object> data = new HashMap<>();
        data.put("aId", 1110011);
        data.put("actionType", "INVEST");
        data.put("investAmount", 50000);
        data.put("investPlan", "33222");
        data.put("cellPhone", "18190800520");
        data.put("level", 4);
        //(注册时间在指定范围内、已经完成实名、且在注册后的20天内完成绑卡
        //运行时计算并加载外部数据
        DrCondition drCondition7 = BaseCondition.newBaseCondition(new MetaCondition("${registerTime}", CompareMethod.BETWEEN,
                Arrays.asList(DateUtils.parseDate("2018-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss"),
                        DateUtils.parseDate("2018-06-01 00:00:00", "yyyy-MM-dd HH:mm:ss"))));
        DrCondition drCondition8 = BaseCondition.newBaseCondition(new MetaCondition("${registerChannel}", CompareMethod.IN,
                Arrays.asList("channelA", "channelB")));
        DrCondition drCondition9 = BaseCondition.newBaseCondition(new MetaCondition("${bindCardTime}", CompareMethod.LESS_AND_EQUAL,
                Arrays.asList("DateUtils.addDays(${registerTime}, 20)")));
        //设置action
        DrRule rule = new DrRule("testRule1");
        rule.setConditions(Arrays.asList(Arrays.asList(drCondition7, drCondition8, drCondition9)));
        rule.setActions(Arrays.asList(new SendAwardAction("award1"), new SendAwardAction("award2")));
        //运行规则
        drRuleEngine.runRuleEngine(data, Arrays.asList(rule));
    }

    @Test
    public void testMultiRule() throws Exception {

//        /**
//         * 准备数据
//         */
//        Map<String, Object> data = new HashMap<>();
//        data.put("userId", 1110011);
//        data.put("actionType", "INVEST");
//        data.put("investAmount", 50000);
//        data.put("level", 4);
//        data.put("investCount", 30);
//
//        /**
//         * 构造规则
//         */
//        DrRule rule = new DrRule("testRuleId2");
//
//        //规则集合1
//        DrCondition drCondition1 = BaseCondition.newBaseCondition(new MetaCondition("${investAmount}", CompareMethod.GRATER, Arrays.asList(50000)));
//        DrCondition drCondition2 = BaseCondition.newBaseCondition(new MetaCondition("${riskEvaluation}", CompareMethod.EQUAL, Arrays.asList(true)));
//        List<DrCondition> conditionGroup1 = Arrays.asList(drCondition1, drCondition2);
//
//        //规则集合2
//        DrCondition drCondition3 = StreamCondition.newStreamCondition("STREAM_INVEST", new Duration(DurationType.LAST_DAYS, 7), null, ReduceType.COUNT, null, CompareMethod.GRATER, 5);
//        DrCondition drCondition4 = StreamCondition.newStreamCondition("STREAM_INVEST", new Duration(DurationType.LAST_DAYS, 7), null, ReduceType.AVERAGE, "${InvestAmount}", CompareMethod.GRATER, 1000);
//        List<DrCondition> conditionGroup2 = Arrays.asList(drCondition3, drCondition4);
//
//        //规则集合3
//        DrCondition drCondition5 = StreamCondition.newStreamCondition("STREAM_INVEST", new Duration(DurationType.NATURE_MONTH), null, ReduceType.COUNT, null, CompareMethod.GRATER, 3);
//        DrCondition drCondition6 = StreamCondition.newStreamCondition("STREAM_COMMENTS", new Duration(DurationType.NATURE_MONTH), null, ReduceType.COUNT, null, CompareMethod.GRATER, 10);
//        List<DrCondition> conditionGroup3 = Arrays.asList(drCondition5, drCondition6);
//
//        //设置action
//        rule.setConditions(Arrays.asList(conditionGroup1, conditionGroup2, conditionGroup3));   //优先级按插入顺序，满足则跳出直接执行actions
//        rule.setActions(Arrays.asList(new SendAwardAction("award1"), new SendAwardAction("award2")));
//
//        /**
//         * 运行规则
//         */
//        drRuleEngine.runRuleEngine(data, Arrays.asList(rule));
    }
}
