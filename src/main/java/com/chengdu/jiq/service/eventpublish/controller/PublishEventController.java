package com.chengdu.jiq.service.eventpublish.controller;

import com.chengdu.jiq.common.annotation.ControllerAspect;
import com.chengdu.jiq.service.eventpublish.service.PublishEventService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiyiqin on 2017/11/18.
 */
@ControllerAspect
@RestController
@RequestMapping("/event-publish")
public class PublishEventController {

    @Autowired
    private PublishEventService publishEventService;

    @ApiOperation(value = "发布spring事件", notes = "发布spring事件", httpMethod = "GET", response = Boolean.class)
    @RequestMapping(value = "/publish", method = RequestMethod.GET)
    public Boolean publish(@RequestParam("eventParam") String eventParam) {
        return publishEventService.publish(eventParam);
    }
}
