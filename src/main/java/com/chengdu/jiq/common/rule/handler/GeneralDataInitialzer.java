package com.chengdu.jiq.common.rule.handler;

import com.chengdu.jiq.common.rule.handler.initializer.AbstractDataInitializer;
import com.chengdu.jiq.common.rule.model.MetaCondition;
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
public class GeneralDataInitialzer implements InitializingBean {

    @Autowired(required = false)
    private List<AbstractDataInitializer> initializers;
    private Map<String, AbstractDataInitializer> initializerMap;

    public Object initialize(Map<String, Object> data, MetaCondition metaCondition) {
        Object value = data.get(metaCondition.getDataKey());
        if (null == value) {
            return initializerMap.get(metaCondition.getDataKey()).initialize(data, metaCondition);
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(initializers)) {
            initializerMap = initializers.stream().collect(Collectors.toMap(e -> e.dataKey(), e -> e));
        }
    }
}
