package com.chengdu.jiq.service.mvc;

import com.chengdu.jiq.common.annotation.ControllerAspect;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
