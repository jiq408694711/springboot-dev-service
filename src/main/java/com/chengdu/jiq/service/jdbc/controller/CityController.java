package com.chengdu.jiq.service.jdbc.controller;

import com.chengdu.jiq.common.annotation.ControllerAspect;
import com.chengdu.jiq.model.bo.CityModel;
import com.chengdu.jiq.model.vo.CityResponse;
import com.chengdu.jiq.service.jdbc.service.CityService;
import com.chengdu.jiq.service.jdbc.support.CitySupport;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jiyiqin on 2017/11/5.
 */
@ControllerAspect
@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @ApiOperation(value = "列出所有城市", notes = "列出所有城市", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/listCitiesByJdbcTemplate", method = RequestMethod.GET)
    public List<CityResponse> listCitiesByJdbcTemplate() {
        List<CityModel> models = cityService.listByJdbcTemplate();
        return models == null ? new ArrayList<>() : models.parallelStream().map(model -> CitySupport.convert2Response(model)).collect(Collectors.toList());
    }

    @ApiOperation(value = "列出所有城市", notes = "列出所有城市", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/listCitiesByMybatis", method = RequestMethod.GET)
    public List<CityResponse> listCitiesByMybatis() {
        List<CityModel> models = cityService.listByMybatis();
        return models == null ? new ArrayList<>() : models.parallelStream().map(model -> CitySupport.convert2Response(model)).collect(Collectors.toList());
    }

    @ApiOperation(value = "列出所有城市", notes = "列出所有城市", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/listByDynamicMybatisFromSlave", method = RequestMethod.GET)
    public List<CityResponse> listByDynamicMybatisFromSlave() {
        List<CityModel> models = cityService.listByDynamicMybatisFromSlave();
        return models == null ? new ArrayList<>() : models.parallelStream().map(model -> CitySupport.convert2Response(model)).collect(Collectors.toList());
    }

    @ApiOperation(value = "更新特定城市", notes = "更新特定城市", httpMethod = "GET", response = String.class)
    @RequestMapping(value = "/updateByDynamicMybatisFromMaster", method = RequestMethod.GET)
    public Boolean updateByDynamicMybatisFromMaster() {
        return cityService.updateByDynamicMybatisFromMaster() == 1;
    }
}
