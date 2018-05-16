package com.chengdu.jiq.common.rule.engine;

import com.chengdu.jiq.common.rule.model.DrAction;
import com.chengdu.jiq.common.rule.model.DrCondition;
import com.chengdu.jiq.common.rule.model.DrRule;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jiyiqin on 2018/5/17.
 */
@Component
public class DrRuleEngine {

    public List<DrAction> runRuleEngine(Map<String, Object> data, List<DrRule> rules) {
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
            params.put("ifCondition", generateCondition(rule.getConditions()));
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
        kieSession.insert(data);   //插入准备过规则的用户数据
        kieSession.insert(rules.parallelStream().collect(Collectors.toMap(e -> e.getId().toString(), e -> e.getActions()))); //插入规则Map(用于筛选执行成功的规则)

        /**
         * 执行规则
         */
        int ruleFiredCount = kieSession.fireAllRules();
        System.out.println("触发了" + ruleFiredCount + "条规则.");
        kieSession.dispose();
        return list;
    }

    private String generateCondition(List<DrCondition> conditions) {
        return "";
    }
}
