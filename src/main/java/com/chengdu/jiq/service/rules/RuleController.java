package com.chengdu.jiq.service.rules;

import com.chengdu.jiq.model.bo.AwardModel;
import com.chengdu.jiq.model.bo.InvestMessageModel;
import com.chengdu.jiq.model.bo.RuleModel;
import com.chengdu.jiq.model.bo.UserDataModel;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jiyiqin on 2018/3/28.
 */
@RequestMapping("/test")
@Controller
public class RuleController {

//    @Autowired
//    private KieSession kieSession;

    @Autowired
    private RuleService ruleService;

    @ResponseBody
    @RequestMapping("/rule")
    public void test() {
        UserDataModel userDataModel = new UserDataModel();
        userDataModel.setName("99425");
        userDataModel.setFirstInvest(true);
        userDataModel.setInvestAmount(new BigDecimal("66666"));

        //        List<String> rules = DataHubRepository.getRulesFromDatabase(dataSourceDhsathub);

        //rule1
        RuleModel rule1 = new RuleModel();
        rule1.setConditions("investAmount > 1000, firstInvest == true");
        rule1.setId(1L);
        AwardModel award = new AwardModel();
        award.setAwardId("10031");
        award.setAwardType("SEND_VIRTUAL_LPD");
        rule1.getAwards().add(award);

        //rule12
        RuleModel rule2 = new RuleModel();
        rule2.setConditions("investAmount > 300");
        rule2.setId(2L);
        AwardModel award2 = new AwardModel();
        award2.setAwardId("10032");
        award2.setAwardType("SEND_VIRTUAL_LPD");
        rule2.getAwards().add(award2);

        //rule12
        RuleModel rule3 = new RuleModel();
        rule3.setConditions("investAmount > 300999");
        rule3.setId(3L);
        AwardModel award3 = new AwardModel();
        award3.setAwardId("10033");
        award3.setAwardType("SEND_VIRTUAL_LPD");
        rule3.getAwards().add(award3);

//        ruleService.initContext(Arrays.asList("investAmount", "firstInvest"));
        List<RuleModel> successRuleModels = runRuleEngine(userDataModel, Arrays.asList(rule1, rule2, rule3));
        System.out.println("规则引擎执行结果." + successRuleModels);
    }

    private List<RuleModel> runRuleEngine(UserDataModel userDataModel, List<RuleModel> rules) {
        KieServices kieServices = KieServices.Factory.get();
        KieHelper helper = new KieHelper();
        ObjectDataCompiler compiler = new ObjectDataCompiler();

        /**
         * 转换RuleModel为drools规则
         */
        Iterator<RuleModel> rulesIterator = rules.iterator();
        while (rulesIterator.hasNext()) {
            RuleModel rule = rulesIterator.next();
            Map<String, String> params = new HashMap<>();
            params.put("ruleId", rule.getId().toString());
            params.put("ifCondition", rule.getConditions());
            String generatedDRL = compiler.compile(Arrays.asList(params), Thread.currentThread().getContextClassLoader().getResourceAsStream("template2.drl"));
            helper.addResource(kieServices.getResources().newByteArrayResource(generatedDRL.getBytes()), ResourceType.DRL);
        }
        KieBase kieBase = helper.build();   //构建知识仓库
        KieSession kieSession = kieBase.newKieSession();    //创建到Drools的会话

        /**
         * 插入数据
         */
        List<RuleModel> list = new ArrayList();
        kieSession.setGlobal("myGlobalList", list); //插入全局变量
        kieSession.insert(userDataModel);   //插入准备过规则的用户数据
        kieSession.insert(rules.parallelStream().collect(Collectors.toMap(e -> e.getId().toString(), e -> e))); //插入规则Map(用于筛选执行成功的规则)

        /**
         * 执行规则
         */
        int ruleFiredCount = kieSession.fireAllRules();
        System.out.println("触发了" + ruleFiredCount + "条规则.");
        kieSession.dispose();
        return list;
    }

    @ResponseBody
    @RequestMapping("/rule2")
    public void test2() {
        UserDataModel userDataModel = new UserDataModel();
        userDataModel.getContext().put("investAmount", 666666);
        userDataModel.getContext().put("firstInvest", true);

        KieHelper helper = new KieHelper();
        helper.addResource(ResourceFactory.newClassPathResource("rules/rule5.drl"), ResourceType.DRL);

        KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();
        config.setOption( EventProcessingOption.STREAM );

        KieBase kBase = helper.build(config);
        KieSession kieSession = kBase.newKieSession();
        kieSession.insert(userDataModel);

//        InvestMessageModel i = new InvestMessageModel();
//        i.setActorId("123");
//        i.setInvestAmount(new BigDecimal("3000"));
//        i.setInvestTime(new Date());
//        kieSession.insert(i);
        kieSession.setGlobal("ruleService", ruleService);
        int numberOfRulesFired = kieSession.fireAllRules();
        System.out.println("触发了" + numberOfRulesFired + "条规则.");
        kieSession.dispose();
    }

    @ResponseBody
    @RequestMapping("/rule3")
    public void test3() {
        UserDataModel userDataModel = new UserDataModel();
        userDataModel.getContext().put("investAmount", 500000);
        userDataModel.getContext().put("firstInvest", true);

//        KieHelper helper = new KieHelper();
//        helper.addResource(ResourceFactory.newClassPathResource("rules/rule5.drl"), ResourceType.DRL);

        KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();
        config.setOption( EventProcessingOption.STREAM );

        KieContainer kContainer = KieServices.Factory.get().getKieClasspathContainer();
        KieSession kieSession = kContainer.newKieSession();


//        KieBase kBase = helper.build(config);
//        KieSession kieSession = kBase.newKieSession();
        kieSession.insert(userDataModel);
        kieSession.setGlobal("ruleService", ruleService);
        int numberOfRulesFired = kieSession.fireAllRules();
        System.out.println("触发了" + numberOfRulesFired + "条规则.");
        kieSession.dispose();
    }
}
