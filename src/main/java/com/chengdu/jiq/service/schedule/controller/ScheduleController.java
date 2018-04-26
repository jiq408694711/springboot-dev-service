package com.chengdu.jiq.service.schedule.controller;

import com.chengdu.jiq.common.annotation.ControllerAspect;
import com.chengdu.jiq.service.schedule.ScheduleModel;
import com.chengdu.jiq.service.schedule.ScheduleResponse;
import com.chengdu.jiq.service.schedule.ScheduleType;
import com.chengdu.jiq.service.schedule.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jiyiqin on 2017/11/19.
 */
@ControllerAspect
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation(value = "列出有效定时任务", notes = "列出有效定时任务", httpMethod = "GET", response = ScheduleResponse.class)
    @RequestMapping(value = "/listValidSchedule", method = RequestMethod.GET)
    public List<ScheduleResponse> listValidSchedule() {
        List<ScheduleModel> models = scheduleService.listValidScheduleModel();
        return models.parallelStream().map(model -> convert2Response(model)).collect(Collectors.toList());
    }

    @ApiOperation(value = "新增定时任务", notes = "新增定时任务", httpMethod = "GET", response = ScheduleResponse.class)
    @RequestMapping(value = "/addSchedule", method = RequestMethod.GET)
    public ScheduleResponse addSchedule(@RequestParam("msg") String msg) {
        ScheduleModel model = new ScheduleModel();
        model.setStartDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 3);
        model.setEndDate(calendar.getTime());

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.MINUTE, 1);
        model.setDelayDate(calendar2.getTime());
        model.setScheduleType(ScheduleType.DELAY_DATE);
        model.setStatus("VALID");
        model.setMsg(msg);
        return convert2Response(scheduleService.addScheduleModel(model));
    }

    private ScheduleResponse convert2Response(ScheduleModel model) {
        ScheduleResponse response = new ScheduleResponse();
        response.setId(model.getId());
        response.setMsg(model.getMsg());
        response.setStartDate(model.getStartDate());
        response.setEndDate(model.getEndDate());
        return response;
    }
}
