package com.chengdu.jiq.service.rules.initializer.base;

import com.chengdu.jiq.common.engine.initializer.base.AbstractDataInitializer;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by jiyiqin on 2018/5/20.
 */
@Component
public class BindCardTimeDataInitializer extends AbstractDataInitializer {
    @Override
    public String dataKey() {
        return "bindCardTime";
    }

    @Override
    public Object initialize(Map<String, Object> data) throws Exception {
        return DateUtils.parseDate("2018-03-03 00:00:00", "yyyy-MM-dd HH:mm:ss");
    }
}
