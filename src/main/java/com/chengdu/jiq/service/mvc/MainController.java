package com.chengdu.jiq.service.mvc;

import com.chengdu.jiq.common.annotation.ControllerAspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@ControllerAspect
@Controller
@RequestMapping("/main")
public class MainController {

    @RequestMapping(value = "/home")
    public String mainPage(Model model, HttpSession httpSession) {
        //获取登录用户名
        SecurityContext ctx = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        if (ctx != null) {
            Authentication au = ctx.getAuthentication();
            User userInfo = (User) au.getPrincipal();
            if (userInfo != null) {
                model.addAttribute("username", userInfo.getUsername());
            }
        }
        return "main/home";
    }
}
