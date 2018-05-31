package com.chengdu.jiq.common.engine;

import com.chengdu.jiq.common.engine.condition.BaseCondition;
import com.chengdu.jiq.common.engine.condition.MetaCondition;
import com.chengdu.jiq.common.engine.condition.StreamCondition;
import com.chengdu.jiq.common.engine.condition.enums.CompareMethod;
import com.chengdu.jiq.common.engine.condition.enums.ReduceType;
import com.chengdu.jiq.common.engine.rule.DrAction;
import com.chengdu.jiq.common.engine.rule.DrCondition;
import com.chengdu.jiq.common.engine.rule.DrRule;
import com.chengdu.jiq.common.engine.initializer.GeneralDataInitializer;
import com.chengdu.jiq.common.engine.initializer.StreamDataFilter;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 规则引擎核心类
 * Created by jiyiqin on 2018/5/17.
 */
@Component
public class DrRuleEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(DrRuleEngine.class);
    @Autowired
    private GeneralDataInitializer dataInitializer;
    @Autowired
    private StreamDataFilter dataFilter;

    /**
     * 传入DRL文件路径
     * 执行规则引擎
     *
     * @param data
     * @param classPathFile
     * @param globals
     */
    public void runRuleEngine(Map<String, Object> data, String classPathFile, Map<String, Object> globals) {
        KieHelper helper = new KieHelper();
        helper.addResource(ResourceFactory.newClassPathResource(classPathFile), ResourceType.DRL);
        KieBase kBase = helper.build();
        KieSession kieSession = kBase.newKieSession();
        kieSession.insert(data);
        if (!CollectionUtils.isEmpty(globals)) {
            for (Map.Entry<String, Object> entry : globals.entrySet()) {
                kieSession.setGlobal(entry.getKey(), entry.getValue());
            }
        }
        int ruleFiredCount = kieSession.fireAllRules();
        LOGGER.info("触发了{}条规则.", ruleFiredCount);
        kieSession.dispose();
    }

    /**
     * 传入自定义数据和规则
     * 执行规则引擎
     *
     * @param data
     * @param rules
     * @return
     * @throws Exception
     */
    public List<DrAction> runRuleEngine(Map<String, Object> data, List<DrRule> rules) throws Exception {
        KieServices kieServices = KieServices.Factory.get();
        KieHelper helper = new KieHelper();
        ObjectDataCompiler compiler = new ObjectDataCompiler();

        /**
         * 转换DrRule为drools规则
         */
        Iterator<DrRule> rulesIterator = rules.iterator();
        while (rulesIterator.hasNext()) {
            DrRule rule = rulesIterator.next();
            Map<String, String> params = new HashMap<>();
            params.put("ruleId", rule.getId().toString());
            params.put("ifCondition", buildDroolsConditionDRL(data, rule.getConditions().get(0)));
            String generatedDRL = compiler.compile(Arrays.asList(params), Thread.currentThread().getContextClassLoader().getResourceAsStream("template2.drl"));
            helper.addResource(kieServices.getResources().newByteArrayResource(generatedDRL.getBytes()), ResourceType.DRL);
        }
        KieBase kieBase = helper.build();   //步骤1：构建知识库
        KieSession kieSession = kieBase.newKieSession();    //步骤2：创建到Drools的会话

        /**
         * 插入数据
         */
        Set<String> passRuleSet = new HashSet();
        kieSession.setGlobal("passRuleSet", passRuleSet); //插入执行通过的规则的收集器
        kieSession.setGlobal("dataInitializer", dataInitializer);   //插入外部基本数据加载器
        kieSession.setGlobal("dataFilter", dataFilter); //插入外部流式数据加载器
        kieSession.insert(data);   //插入准备过规则的用户数据

        /**
         * 执行规则
         */
        int ruleFiredCount = kieSession.fireAllRules();
        LOGGER.info("触发了{}条规则.", ruleFiredCount);
        kieSession.dispose();
        List<DrRule> passRules = rules.parallelStream().filter(rule -> passRuleSet.contains(rule.getId())).collect(Collectors.toList());
        List<DrAction> resultActions = new ArrayList<>();
        passRules.stream().forEach(rule -> resultActions.addAll(rule.getActions()));
        return resultActions;
    }

    /**
     * 生成条件部分DRL
     *
     * @param data
     * @param conditions
     * @return
     * @throws Exception
     */
    private String buildDroolsConditionDRL(Map<String, Object> data, List<DrCondition> conditions) throws Exception {
        StringBuilder sb = new StringBuilder("$data: Map();\n");
        for (DrCondition condition : conditions) {
            if (condition instanceof BaseCondition) {
                BaseCondition baseCondition = (BaseCondition) condition;
                //生成Map筛选表达式
                sb.append("Map(");
                List<String> dataKeys = new ArrayList<>();
                Iterator<MetaCondition> iterator = baseCondition.getMetaConditions().iterator();
                while (iterator.hasNext()) {
                    MetaCondition metaCondition = iterator.next();
                    dataKeys.addAll(parseDataKeys(data, metaCondition.getLeft(), metaCondition.getRights()));
                    sb.append(parse$Signature(metaCondition.getLeft()));
                    sb.append(convert2OperatorAndCompareValue(metaCondition.getCompareMethod(), metaCondition.getRights()));
                    if (iterator.hasNext()) {
                        sb.append(" || ");
                    }
                }
                sb.append(")");
                //是否需要加载外部数据
                if (!CollectionUtils.isEmpty(dataKeys)) {
                    StringBuilder keys = new StringBuilder();
                    for (String key : dataKeys) {
                        keys.append("\"" + key + "\",");
                    }
                    sb.append(" from dataInitializer.initialize($data, " + keys.substring(0, keys.length() - 1) + ")");
                }
                sb.append(";\n");
            } else if (condition instanceof StreamCondition) {
                StreamCondition streamCondition = (StreamCondition) condition;
                StreamCondition.Reduce reduce = streamCondition.getReduce();
                //reduce结果比较
                sb.append("Number(");
                sb.append(streamCondition.getCondition().getLeft().replace("${reduceValue}", "doubleValue"));
                sb.append(convert2OperatorAndCompareValue(streamCondition.getCondition().getCompareMethod(), streamCondition.getCondition().getRights()));
                sb.append(") from accumulate(\n");
                sb.append("Map(");
                //过滤条件
                Iterator<MetaCondition> filterConditions = reduce.getFilterConditions().iterator();
                while (filterConditions.hasNext()) {
                    MetaCondition metaCondition = filterConditions.next();
                    sb.append(parse$Signature(metaCondition.getLeft()));
                    sb.append(convert2OperatorAndCompareValue(metaCondition.getCompareMethod(), metaCondition.getRights()));
                    if (filterConditions.hasNext()) {
                        sb.append(",");
                    }
                }
                if (null != reduce.getReduceKey()) {
                    sb.append(",$" + parse$Key(reduce.getReduceKey()) + ": " + parse$Signature(reduce.getReduceKey()));
                }
                sb.append(") from dataFilter.filter($data, \"" + reduce.getStreamKey() + "\",\"" +
                        (reduce.getDuration() == null ? "" : reduce.getDuration().getType().name()) + "\"," +
                        (reduce.getDuration() == null ? null : reduce.getDuration().getValue()) + "," +
                        (reduce.getDuration() == null ? null : reduce.getDuration().getBeginTime()) + "," +
                        (reduce.getDuration() == null ? null : reduce.getDuration().getEndTime()) + "),\n");
                //reduce操作
                if (reduce.getReduceOp() == ReduceType.COUNT) {
                    sb.append("count(1)");
                } else if (reduce.getReduceOp() == ReduceType.AVERAGE) {
                    sb.append("average($" + parse$Key(reduce.getReduceKey()) + ")");
                } else if (reduce.getReduceOp() == ReduceType.SUM) {
                    sb.append("sum($" + parse$Key(reduce.getReduceKey()) + ")");
                } else {
                    throw new Exception("不识别的reduce操作");
                }
                sb.append(");\n");

            } else {
                throw new Exception("不识别的条件类型");
            }

        }
        return sb.toString();
    }

    /**
     * 正则表达式解析出所有需要外部加载的数据的key
     * 这里用到宽断言：
     * (?=exp)	匹配exp前面的位置
     * (?<=exp)	匹配exp后面的位置
     * (?!exp)	匹配后面跟的不是exp的位置
     * (?<!exp)	匹配前面不是exp的位置
     *
     * @param data
     * @param left
     * @param rights
     * @return
     */
    private List<String> parseDataKeys(Map<String, Object> data, String left, List<Object> rights) {
        List<String> dataKeys = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?<=\\$\\{)[^\\}]+");
        //handle left
        Matcher m = pattern.matcher(left);
        while (m.find()) {
            String key = m.group();
            if (!data.containsKey(key) || null == data.get(key)) {
                dataKeys.add(key);
            }
        }
        //handle right
        for (Object right : rights) {
            if (right instanceof String) {
                m = pattern.matcher((String) right);
                while (m.find()) {
                    String key = m.group();
                    if (!data.containsKey(key) || null == data.get(key)) {
                        dataKeys.add(key);
                    }
                }
            }
        }
        return dataKeys;
    }

    /**
     * 生成操作符和右侧表达式
     *
     * @param method
     * @param compareValues
     * @return
     * @throws Exception
     */
    private String convert2OperatorAndCompareValue(CompareMethod method, List<Object> compareValues) throws Exception {
        switch (method) {
            case LESS:
                return " < " + parseCompareValue$Signature(compareValues.get(0));
            case GRATER:
                return " > " + parseCompareValue$Signature(compareValues.get(0));
            case LESS_AND_EQUAL:
                return " <= " + parseCompareValue$Signature(compareValues.get(0));
            case EQUAL:
                return " == " + parseCompareValue$Signature(compareValues.get(0));
            case STR_STARTS_WITH:
                return " str[startsWith] " + parseCompareValue$Signature(compareValues.get(0));
            case STR_END_WITH:
                return " str[endsWith] " + parseCompareValue$Signature(compareValues.get(0));
            case STR_LENGTH:
                return " str[length] " + parseCompareValue$Signature(compareValues.get(0));
            case IN:
                String exp = " in (";
                for (Object o : compareValues) {
                    exp += parseCompareValue$Signature(o) + ",";
                }
                exp = exp.substring(0, exp.length() - 1);
                return exp + ")";
            case BETWEEN:
                return "> " + parseCompareValue$Signature(compareValues.get(0)) + " && < " + parseCompareValue$Signature(compareValues.get(1));
            default:
                throw new Exception("无效的操作符");
        }
    }

    /**
     * 转换${value}为this["value"]
     *
     * @param value
     * @return
     */
    private String parse$Signature(String value) {
        value = value.replaceAll("\\$\\{", "this[\"");
        return value.replaceAll("}", "\"]");
    }

    /**
     * 提取${}中的内容
     *
     * @param str
     * @return
     */
    private String parse$Key(String str) {
        Pattern pattern = Pattern.compile("(?<=\\$\\{)[^\\}]+");
        Matcher m = pattern.matcher(str);
        while (m.find()) {
            return m.group();
        }
        return null;
    }

    /**
     * 解析比较值
     *
     * @param compareValue
     * @return
     */
    private Object parseCompareValue$Signature(Object compareValue) {
        if (compareValue instanceof String) {
            if (compareValue.toString().contains("${")) {
                return parse$Signature(String.valueOf(compareValue));
            } else {
                return "\"" + compareValue + "\"";
            }
        } else if (compareValue instanceof Date) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "DateUtils.parseDate(\"" + formatter.format(compareValue) + "\", \"yyyy-MM-dd HH:mm:ss\")";
        }
        return compareValue;
    }
}
