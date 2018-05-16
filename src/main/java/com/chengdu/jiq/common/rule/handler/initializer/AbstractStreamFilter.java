package com.chengdu.jiq.common.rule.handler.initializer;

import com.chengdu.jiq.common.rule.model.Condition;

import java.util.List;
import java.util.Map;

/**
 * Created by jiyiqin on 2018/5/16.
 */
public abstract class AbstractStreamFilter {

    public abstract String streamKey();

    public abstract List<Map<String, Object>> filter(Condition condition);
}
