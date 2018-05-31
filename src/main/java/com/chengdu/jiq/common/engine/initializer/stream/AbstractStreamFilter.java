package com.chengdu.jiq.common.engine.initializer.stream;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jiyiqin on 2018/5/16.
 */
public abstract class AbstractStreamFilter {

    public abstract String streamKey();

    public abstract List<Map<String, Object>> filter(Map<String, Object> data, Date beginDate, Date endDate, Integer limit);
}
