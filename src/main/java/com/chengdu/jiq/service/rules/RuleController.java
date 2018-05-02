package com.chengdu.jiq.service.rules;

import com.chengdu.jiq.model.bo.AwardModel;
import com.chengdu.jiq.model.bo.RuleCheckResult;
import com.chengdu.jiq.model.bo.RuleModel;
import com.chengdu.jiq.model.bo.UserDataModel;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.StringReader;
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

    private KieSession kieSessionWithoutTemplate() {
//        List<String> rules = DataHubRepository.getRulesFromDatabase(dataSourceDhsathub);

        KieHelper helper = new KieHelper();
//        String rule = "package boot\n" +
//                "\n" +
//                "rule \"\"rule1\"\"\n" +
//                "when\n" +
//                "\t$o: Object()\n" +
//                "then\n" +
//                "\tSystem.out.println($o);\n" +
//                "end\n";
        String rule = "package com.chengdu.jiq.drools1\n" +
                "\n" +
                "import com.chengdu.jiq.model.bo.UserDataModel;\n" +
                "import com.chengdu.jiq.model.bo.RuleCheckResult;\n" +
                "\n" +
                "rule \"first rule\"\n" +
                "    when\n" +
                "        userData : UserDataModel(investAmount > 1000, firstInvest == true)\n" +
                "        checkResult : RuleCheckResult();\n" +
                "    then\n" +
                "        checkResult.setResult(true);\n" +
                "        System.out.println(\"规则中打印日志：校验通过!\");\n" +
                "end";
//        Iterator<String> rulesIterator = rules.iterator();
//        while (rulesIterator.hasNext()){
//        Resource resource = ResourceFactory.newReaderResource(new StringReader(rule));
//        resource.setResourceType(ResourceType.DRL);
//        resource.setSourcePath("myrule");
//        resource.setTargetPath("myrule");
//        helper.addResource(resource);

        helper.addResource(ResourceFactory.newReaderResource(new StringReader(rule)), ResourceType.DRL);

//        KieServices kieServices = KieServices.Factory.get();
//        Resource resource1 = kieServices.getResources().newByteArrayResource(rule.getBytes());
//        helper.addResource(resource1, ResourceType.DRL);

//        }
        KieBase kBase = helper.build();
        KieSession kieSession = kBase.newKieSession();
//        kieSession.setGlobal("o", "33333");
//        kieSession.insert("44444");
//        int numberOfRulesFired = kieSession.fireAllRules();
//        kieSession.dispose();
        return kieSession;
    }

    private void runWithTemplate() {
        List<Map<String, String>> ruleAttributes = new ArrayList<>();
        Map<String, String> rule1 = new HashMap<>();
        rule1.put("ruleid", "2");
        rule1.put("ifcondition", "abc: Abc(xyz.getId() == 2);");
        rule1.put("thencondition", "myGlobal.setPqr(200.1D);");
        ruleAttributes.add(rule1);
        ObjectDataCompiler compiler = new ObjectDataCompiler();
//        String rule = "package boot\n" +
//                "\n" +
//                "rule \"\"@{ruleid}\"\"\n" +
//                "when\n" +
//                "\t$o: Object()\n" +
//                "then\n" +
//                "\tSystem.out.println($o);\n" +
//                "end\n";
//        String generatedDRL = compiler.compile(ruleAttributes, new ByteArrayInputStream(rule.getBytes()));
        String generatedDRL = compiler.compile(ruleAttributes, Thread.currentThread().getContextClassLoader().getResourceAsStream("template.drl"));

        KieServices kieServices = KieServices.Factory.get();

        KieHelper kieHelper = new KieHelper();

        //multiple such resoures/rules can be added
        byte[] b1 = generatedDRL.getBytes();
        Resource resource1 = kieServices.getResources().newByteArrayResource(b1);
        kieHelper.addResource(resource1, ResourceType.DRL);

        KieBase kieBase = kieHelper.build();

        KieSession kieSession = kieBase.newKieSession();
        kieSession.setGlobal("o", "33333");
        kieSession.insert("44444");
        int numberOfRulesFired = kieSession.fireAllRules();
        kieSession.dispose();
    }

//    private void test2() throws UnsupportedEncodingException {
//        String rule = "package com.fei.drools\r\n";
//        rule += "import com.fei.drools.Message;\r\n";
//        rule += "rule \"rule1\"\r\n";
//        rule += "\twhen\r\n";
//        rule += "Message( status == 1, myMessage : msg )";
//        rule += "\tthen\r\n";
//        rule += "\t\tSystem.out.println( 1+\":\"+myMessage );\r\n";
//        rule += "end\r\n";
//
//
//        String rule2 = "package com.fei.drools\r\n";
//        rule += "import com.fei.drools.Message;\r\n";
//
//        rule += "rule \"rule2\"\r\n";
//        rule += "\twhen\r\n";
//        rule += "Message( status == 2, myMessage : msg )";
//        rule += "\tthen\r\n";
//        rule += "\t\tSystem.out.println( 2+\":\"+myMessage );\r\n";
//        rule += "end\r\n";
//        StatefulKnowledgeSession kSession = null;
//        try {
//
//            KieServices kieServices = KieServices.Factory.get();
//
//            KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
//            KieBuilder kieBuilder = kieServices.newKieBuilder( kfs ).buildAll();
//
//            //装入规则，可以装入多个
//            kb.add(ResourceFactory.newByteArrayResource(rule.getBytes("utf-8")), ResourceType.DRL);
//            kb.add(ResourceFactory.newByteArrayResource(rule2.getBytes("utf-8")), ResourceType.DRL);
//
//            KnowledgeBuilderErrors errors = kb.getErrors();
//            for (KnowledgeBuilderError error : errors) {
//                System.out.println(error);
//            }
//
//
//
////            KieContainer kieContainer =
////                    kieServices.newKieContainer( kieBuilder.getKieModule().getReleaseId() );
////            KieSession kieSession = kieContainer.newKieSession();
////
////            KieServices ks = KieServices.Factory.get();
////            KieBuilder kieBuilder = ks.newKieBuilder(kieFileSystem());
////            kieBuilder.buildAll();
////            KieContainer kc = ks.getKieClasspathContainer();
////            KieSession ksession = kc.newKieSession("HelloWorldKS");
//
//            KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
//            kBase.addKnowledgePackages(kb.getKnowledgePackages());
//
//            kSession = kBase.newStatefulKnowledgeSession();
//
//
////            Message message1 = new Message();
////            message1.setStatus(1);
////            message1.setMsg("hello world!");
////
////            Message message2 = new Message();
////            message2.setStatus(2);
////            message2.setMsg("hi world!");
////
////            kSession.insert(message1);
////            kSession.insert(message2);
//            kSession.fireAllRules();
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } finally {
//            if (kSession != null)
//                kSession.dispose();
//        }
//
//    }
}
