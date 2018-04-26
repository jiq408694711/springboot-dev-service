package com.chengdu.jiq.service.mail;

import com.chengdu.jiq.common.annotation.ControllerAspect;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;

@ControllerAspect
@RestController
@RequestMapping("/mail")
public class MailSendController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailSendController.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    @ApiOperation(value = "", notes = "", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String hello(@RequestParam String toAddress) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(username);
            helper.setTo(toAddress);
            helper.setSubject("测试邮件");
            helper.setText("测试邮件内容");
//            FileSystemResource image = new FileSystemResource("/img/kg.jpg");
//            helper.addAttachment("test.png", image);
            javaMailSender.send(message);
        } catch (Exception e) {
            LOGGER.info("Send Mail Exception: {}", e);
        }
        return "send success";
    }
}
