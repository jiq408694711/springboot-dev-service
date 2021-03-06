package com.chengdu.jiq.service.rules.initializer.base;

import com.chengdu.jiq.common.engine.initializer.base.AbstractDataInitializer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by jiyiqin on 2018/5/20.
 */
@Component
public class RealNameDataInitializer extends AbstractDataInitializer {

    @Override
    public String dataKey() {
        return "hasRealName";
    }

    @Override
    public Object initialize(Map<String, Object> data) {
        return true;
    }
}
