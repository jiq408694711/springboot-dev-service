package com.chengdu.jiq.service.mvc;

import com.chengdu.jiq.common.annotation.ControllerAspect;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jiyiqin on 2017/9/16.
 */

@ControllerAspect
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/list")
    public String user() {
        return "user/userlist";
    }

    @RequestMapping(value = "/list2")
    public ModelAndView user2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelMap model = new ModelMap();
        model.addAttribute("courtName", "yiqin");
        return new ModelAndView("user/userlist", model);
    }
}
