package com.chengdu.jiq.common.rule.handler;

import com.chengdu.jiq.common.rule.handler.initializer.AbstractStreamFilter;
import com.chengdu.jiq.common.rule.model.DrCondition;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jiyiqin on 2018/5/16.
 */
public class StreamDataFilter implements InitializingBean {

    @Autowired(required = false)
    private List<AbstractStreamFilter> filters;
    private Map<String, AbstractStreamFilter> filterMap;

    public List<Map<String, Object>> filter(Map<String, Object> data, DrCondition condition) {
        return filterMap.get(condition.getStreamKey()).filter(data, condition);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(filters)) {
            filterMap = filters.stream().collect(Collectors.toMap(e -> e.streamKey(), e -> e));
        }
    }
}
