package com.chengdu.jiq.service.rules;

import com.chengdu.jiq.model.bo.RuleModel;
import com.chengdu.jiq.model.bo.UserDataModel;
import com.chengdu.jiq.model.bo.RuleCheckResult;
import com.chengdu.jiq.model.bo.AwardModel;
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

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.*;

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
    public void test(){
        UserDataModel userDataModel = new UserDataModel();
        userDataModel.setName("99425");
        userDataModel.setFirstInvest(true);
        userDataModel.setInvestAmount(new BigDecimal("66666"));

        //condition
        RuleModel rule = new RuleModel();
        String conditions = "investAmount > 1000, firstInvest == true";
        rule.setConditions(conditions);

        //award
        RuleCheckResult result = new RuleCheckResult();
        AwardModel award = new AwardModel();
        award.setAwardId("10031");
        award.setAwardType("SEND_VIRTUAL_LPD");
        result.getAwards().add(award);
        rule.setResult(result);

//        ruleService.initContext(Arrays.asList("investAmount", "firstInvest"));
        KieSession kieSession = kieSession(Arrays.asList(rule));

//        kieSession.insert(ruleService);
        int ruleFiredCount = kieSession.fireAllRules();
        System.out.println("触发了" + ruleFiredCount + "条规则");

        if(result.isResult()){
            System.out.println("规则校验通过");
        }

    }

    private KieSession kieSession(List<RuleModel> rules) {
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
        List<Map<String, String>> ruleAttributes = new ArrayList<>();
        Map<String, String> rule1 = new HashMap<>();
        rule1.put("ruleId", "2");
        rule1.put("ifCondition", rules);
//        rule1.put("thencondition", "myGlobal.setPqr(200.1D);");

//        String rule = "template header\n" +
//                "ruleId\n" +
//                "ifCondition\n" +
//                "package com.chengdu.jiq.drools1\n" +
//                "import com.chengdu.jiq.model.bo.UserDataModel;\n" +
//                "import com.chengdu.jiq.model.bo.RuleCheckResult;\n" +
////                "global com.chengdu.jiq.model.bo.RuleCheckResult checkResult;\n" +
//                "template \"tmp1\"\n" +
//                "rule \"@{ruleId}\"\n" +
//                "    when\n" +
//                "        address : UserDataModel(@{ifCondition})\n" +
//                "        checkResult : RuleCheckResult();\n" +
//                "    then\n" +
//                "        checkResult.setResult(true);\n" +
//                "        System.out.println(\"规则中打印日志：校验通过!\");\n" +
//                "end\n" +
//                "end template";
//        Iterator<String> rulesIterator = rules.iterator();
//        while (rulesIterator.hasNext()){
//        Resource resource = ResourceFactory.newReaderResource(new StringReader(rule));
//        resource.setResourceType(ResourceType.DRL);
//        resource.setSourcePath("myrule");
//        resource.setTargetPath("myrule");
//        helper.addResource(resource);

        ObjectDataCompiler compiler = new ObjectDataCompiler();
//        String generatedDRL = compiler.compile(ruleAttributes, new ByteArrayInputStream(rule.getBytes()));

//        helper.addResource(ResourceFactory.newReaderResource(new StringReader(generatedDRL)), ResourceType.DRL);
        String generatedDRL = compiler.compile(ruleAttributes, Thread.currentThread().getContextClassLoader().getResourceAsStream("template2.drl"));

//        KieServices kieServices = KieServices.Factory.get();
//        Resource resource1 = kieServices.getResources().newByteArrayResource(rule.getBytes());
//        helper.addResource(resource1, ResourceType.DRL);

//        }
        KieBase kBase = helper.build();
        KieSession kieSession = kBase.newKieSession();

        kieSession.insert(userDataModel);
        kieSession.insert(result);
//        kieSession.setGlobal("o", "33333");
//        kieSession.insert("44444");
//        int numberOfRulesFired = kieSession.fireAllRules();
//        kieSession.dispose();
        return kieSession;
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
