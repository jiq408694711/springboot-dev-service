package com.chengdu.jiq.service.rules;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiyiqin on 2018/3/30.
 */

@Service
public class RuleService {

    public void hello() {
        System.out.println("service called");
    }

    public void initContext(List<String> keys) {
        System.out.println("当前规则需要初始化的条件包括：" + keys);
    }
}
