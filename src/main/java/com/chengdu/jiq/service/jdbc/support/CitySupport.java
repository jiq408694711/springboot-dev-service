package com.chengdu.jiq.service.jdbc.support;

import com.chengdu.jiq.model.bo.CityModel;
import com.chengdu.jiq.model.vo.CityResponse;
import org.springframework.beans.BeanUtils;

/**
 * Created by jiyiqin on 2017/11/5.
 */
public class CitySupport {

    public static CityResponse convert2Response(CityModel model) {
        CityResponse resp = new CityResponse();
        BeanUtils.copyProperties(model, resp);
        return resp;
    }
}
