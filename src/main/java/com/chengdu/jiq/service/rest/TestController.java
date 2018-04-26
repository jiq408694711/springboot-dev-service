package com.chengdu.jiq.service.rest;

import com.chengdu.jiq.common.annotation.ControllerAspect;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@ControllerAspect
@RestController
@RequestMapping("/rest")
public class TestController {

    @ApiOperation(value = "", notes = "", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam String name) {
        return "rest," + name;
    }

    @ApiOperation(value = "", notes = "", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/testJsonConvertor", method = RequestMethod.GET)
    public HelloResponse testJsonConvertor(@RequestParam String name) {
        HelloResponse response = new HelloResponse();
        response.setName(name);
        response.setTime(new Date());
        response.setAge(null);
        /**
         * 不定制化Spring mvc的MappingJackson2HttpMessageConverter
         * 就会按照下面转换json
         * {
         "name": "32432",
         "time": 1511365802841,
         "age": null
         }
         */
        return response;
    }

    @ApiOperation(value = "", notes = "", httpMethod = "POST", response = String.class)
    @RequestMapping(value = "/hello-request", method = RequestMethod.POST)
    public String hello(@RequestBody HelloRequest request) {
        return "rest," + request.getId();
    }
}
