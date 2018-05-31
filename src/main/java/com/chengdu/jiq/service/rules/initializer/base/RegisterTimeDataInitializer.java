package com.chengdu.jiq.service.rules.initializer.base;

import com.chengdu.jiq.common.engine.initializer.base.AbstractDataInitializer;
import com.chengdu.jiq.service.rules.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by jiyiqin on 2018/5/20.
 */
@Component
public class RegisterTimeDataInitializer extends AbstractDataInitializer {
    @Override
    public String dataKey() {
        return "registerTime";
    }

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public Object initialize(Map<String, Object> data) throws Exception {
        if (!data.containsKey("aId") || data.get("aId") == null) {
            return null;
        }
        //search user info from database
        return userInfoRepository.getUserInfoByAid(data.get("aId").toString()).getRegisterTime();
    }
}
