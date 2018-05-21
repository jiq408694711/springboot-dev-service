package com.chengdu.jiq.common.rule.handler.initializer.initialzer.impl;

import com.chengdu.jiq.common.rule.handler.initializer.initialzer.AbstractDataInitializer;
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

    @Override
    public Object initialize(Map<String, Object> data) {
        return "2018-03-01 00:00:00";
    }
}