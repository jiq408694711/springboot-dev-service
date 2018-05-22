package com.chengdu.jiq.common.rule.engine;

import com.chengdu.jiq.common.rule.handler.GeneralDataInitializer;
import com.chengdu.jiq.common.rule.handler.StreamDataFilter;
import com.chengdu.jiq.common.rule.model.DrAction;
import com.chengdu.jiq.common.rule.model.DrCondition;
import com.chengdu.jiq.common.rule.model.DrRule;
import com.chengdu.jiq.common.rule.model.condition.BaseCondition;
import com.chengdu.jiq.common.rule.model.condition.StreamCondition;
import com.chengdu.jiq.common.rule.model.enums.CompareMethod;
import com.chengdu.jiq.common.rule.model.enums.ReduceType;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by jiyiqin on 2018/5/17.
 */
@Component
public class DrRuleEngine {

    @Autowired
    private GeneralDataInitializer dataInitializer;

    @Autowired
    private StreamDataFilter dataFilter;

    public List<DrAction> runRuleEngine(Map<String, Object> data, List<DrRule> rules) throws Exception {
        KieServices kieServices = KieServices.Factory.get();
        KieHelper helper = new KieHelper();
        ObjectDataCompiler compiler = new ObjectDataCompiler();

        /**
         * 转换RuleModel为drools规则
         */
        Iterator<DrRule> rulesIterator = rules.iterator();
        while (rulesIterator.hasNext()) {
            DrRule rule = rulesIterator.next();
            Map<String, String> params = new HashMap<>();
            params.put("ruleId", rule.getId().toString());
            params.put("ifCondition", generateCondition(data, rule.getConditions().get(0)));
            String generatedDRL = compiler.compile(Arrays.asList(params), Thread.currentThread().getContextClassLoader().getResourceAsStream("template2.drl"));
            helper.addResource(kieServices.getResources().newByteArrayResource(generatedDRL.getBytes()), ResourceType.DRL);
        }
        KieBase kieBase = helper.build();   //构建知识仓库
        KieSession kieSession = kieBase.newKieSession();    //创建到Drools的会话

        /**
         * 插入数据
         */
        List<DrAction> list = new ArrayList();
        kieSession.setGlobal("myGlobalList", list); //插入全局变量
        kieSession.setGlobal("dataInitializer", dataInitializer);
        kieSession.setGlobal("dataFilter", dataFilter);
        kieSession.insert(data);   //插入准备过规则的用户数据
//        kieSession.insert(rules.parallelStream().collect(Collectors.toMap(e -> e.getId(), e -> e.getActions()))); //插入规则Map(用于筛选执行成功的规则)

        /**
         * 执行规则
         */
        int ruleFiredCount = kieSession.fireAllRules();
        System.out.println("触发了" + ruleFiredCount + "条规则.");
        kieSession.dispose();
        return list;
    }

    private String generateCondition(Map<String, Object> data, List<DrCondition> conditions) throws Exception {
        StringBuilder sb = new StringBuilder("$data: Map();\n");
        for (DrCondition condition : conditions) {
            if (condition instanceof BaseCondition) {
                BaseCondition baseCondition = (BaseCondition) condition;
                String left = baseCondition.getMetaConditions().get(0).getLeft();
                sb.append("Map(");
                sb.append(parse$Signature(left));
                sb.append(convert2OperatorAndCompareValue(baseCondition.getMetaConditions().get(0).getCompareMethod(), baseCondition.getMetaConditions().get(0).getRights()));
                sb.append(")");

//                    Pattern p = Pattern.compile("(\\$\\{[^\\]]*\\])");
//                    Matcher m = p.matcher(dataKey);
//                    String varName = remove$Signature(m.group(0));

                String v = "registerTime";
                String v2 = "realName";
                String v3 = "inviteeRegisterCount";
                if (left.equals("${" + v + "}")) {
                    sb.append(" from dataInitializer.initialize($data, \"" + v + "\")");
                }
                if (left.equals("${" + v2 + "}")) {
                    sb.append(" from dataInitializer.initialize($data, \"" + v2 + "\")");
                }
                if (left.equals("${" + v3 + "} % 2")) {
                    sb.append(" from dataInitializer.initialize($data, \"" + v3 + "\")");
                }
                sb.append(";\n");
            } else if (condition instanceof StreamCondition) {
                if (((StreamCondition) condition).getReduceOp().equals(ReduceType.COUNT)) {
                    sb.append("Number(intValue > 3 ) from accumulate(\n" +
                            "            Map(this[\"investAmount\"] > 1000) from dataFilter.filter(\"STREAM_INVEST\"),\n" +
                            "            count(1));\n");
                }
                if (((StreamCondition) condition).getReduceOp().equals(ReduceType.SUM)) {
                    sb.append("Number(doubleValue > 50000 ) from accumulate(\n" +
                            "            Map($investAmount:this[\"investAmount\"]) from dataFilter.filter(\"STREAM_INVEST\"),\n" +
                            "            sum($investAmount));\n");
                }
            } else {
                throw new Exception("不识别的条件类型");
            }

        }
        return sb.toString();
    }

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

    private String parse$Signature(String value) {
        value = value.replaceAll("\\$\\{", "this[\"");
        return value.replaceAll("}", "\"]");
    }

    private String remove$Signature(String value) {
        value = value.replaceAll("\\$\\{", "");
        return value.replaceAll("}", "");
    }

    private Object parseCompareValue$Signature(Object compareValue) {
        if (compareValue instanceof String) {
            if (compareValue.toString().contains("${")) {
                return parse$Signature(String.valueOf(compareValue));
            } else {
                return "\"" + compareValue + "\"";
            }
        }
        return compareValue;

    }
}
