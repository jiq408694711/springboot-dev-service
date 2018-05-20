package com.chengdu.jiq.common.rule.handler;

import com.chengdu.jiq.common.rule.handler.initializer.initialzer.AbstractDataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jiyiqin on 2018/5/16.
 */
@Component
public class GeneralDataInitializer implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralDataInitializer.class);

    @Autowired(required = false)
    private List<AbstractDataInitializer> initializerList;
    private Map<String, AbstractDataInitializer> initializerMap;

    public Map<String, Object> initialize(Map<String, Object> data, String dataKey) {
        LOGGER.info("init:{}", dataKey);
        Map<String, Object> map = new HashMap<>();
        map.put(dataKey, initializerMap.get(dataKey).initialize(data));
        return map;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(initializerList)) {
            initializerMap = initializerList.stream().collect(Collectors.toMap(e -> e.dataKey(), e -> e));
        }
    }
}
