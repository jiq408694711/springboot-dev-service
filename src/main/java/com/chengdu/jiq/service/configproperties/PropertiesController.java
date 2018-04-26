package com.chengdu.jiq.service.configproperties;

import com.chengdu.jiq.common.annotation.ControllerAspect;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiyiqin on 2017/11/25.
 */
@ControllerAspect
@RestController
@RequestMapping("/properties")
public class PropertiesController {

    @Autowired
    private FooProperties fooProperties;

    @RequestMapping(value = "/show")
    @ApiOperation(value = "", notes = "", httpMethod = "GET", response = FooProperties.class)
    public FooProperties show() {
        return fooProperties;
    }
}