package com.chengdu.jiq.service.rules;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 * Created by jiyiqin on 2018/5/26.
 */
@Service
public class UserInfoRepository {
    public UserInfo getUserInfoByAid(String aid) {
        UserInfo userInfo = new UserInfo();
        try {
            userInfo.setRegisterTime(DateUtils.parseDate("2018-03-01 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
