package com.chengdu.jiq.common.rule.handler.initializer;

import com.chengdu.jiq.common.rule.model.MetaCondition;

import java.util.Map;

/**
 * Created by jiyiqin on 2018/5/16.
 */
public abstract class AbstractDataInitializer {

    public abstract String dataKey();

    public abstract Object initialize(Map<String, Object> data, MetaCondition metaCondition);
}
