package com.chengdu.jiq.common.rule.handler;

import com.chengdu.jiq.common.rule.handler.initializer.filter.AbstractStreamFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jiyiqin on 2018/5/16.
 */
@Component
public class StreamDataFilter implements InitializingBean {

    @Autowired(required = false)
    private List<AbstractStreamFilter> filterList;
    private Map<String, AbstractStreamFilter> filterMap;

    public List<Map<String, Object>> filter(String streamKey) {
        return filterMap.get(streamKey).filter();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(filterList)) {
            filterMap = filterList.stream().collect(Collectors.toMap(e -> e.streamKey(), e -> e));
        }
    }
}
