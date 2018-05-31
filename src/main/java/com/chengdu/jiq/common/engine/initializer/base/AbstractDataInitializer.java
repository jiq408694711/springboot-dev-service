package com.chengdu.jiq.common.engine.initializer.base;

import java.util.Map;

/**
 * Created by jiyiqin on 2018/5/16.
 */
public abstract class AbstractDataInitializer {

    public abstract String dataKey();

    public abstract Object initialize(Map<String, Object> data) throws Exception;
}
