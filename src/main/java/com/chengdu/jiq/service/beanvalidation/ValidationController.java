package com.chengdu.jiq.service.beanvalidation;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by jiyiqin on 2017/12/3.
 */
@Configuration
@RestController
@RequestMapping("/validation")
public class ValidationController {

    @Autowired
    private ValidationService validationService;

    @ApiOperation(value = "", notes = "", httpMethod = "POST", response = Boolean.class)
    @RequestMapping(value = "/validation-in-service", method = RequestMethod.POST)
    public Boolean validationInService(@Valid @RequestBody UserCreateRequest request) {
        return validationService.create(request.getName(), request.getAge());
    }
}
