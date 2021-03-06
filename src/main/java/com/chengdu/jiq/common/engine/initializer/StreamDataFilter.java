package com.chengdu.jiq.common.engine.initializer;

import com.chengdu.jiq.common.engine.initializer.stream.AbstractStreamFilter;
import com.chengdu.jiq.common.engine.condition.enums.DurationType;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
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

    /**
     * 根据时间限制返回过滤后的流式数据集
     *
     * @param data
     * @param streamKey
     * @param type
     * @param value
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Map<String, Object>> filter(Map<String, Object> data,
                                            String streamKey,
                                            String type,
                                            Integer value,
                                            Long beginTime,
                                            Long endTime) {
        Date beginDate = parseBeginDate(type, value, beginTime, endTime);
        Date endDate = parseEndDate(type, value, beginTime, endTime);
        Integer limit = parseLimit(type, value);
        return filterMap.get(streamKey).filter(data, beginDate, endDate, limit);
    }

    private Integer parseLimit(String type, Integer value) {
        if (type.equals(DurationType.LAST_DAYS.name())) {
            return null;
        } else if (type.equals(DurationType.AHEAD_LENGTH.name())) {
            return value;
        }
        return null;
    }

    private Date parseEndDate(String type, Integer value, Long beginTime, Long endTime) {
        if (type.equals(DurationType.LAST_DAYS.name())) {
            return new Date();
        } else if (type.equals(DurationType.AHEAD_LENGTH.name())) {
            return null;
        }
        return null;
    }

    private Date parseBeginDate(String type, Integer value, Long beginTime, Long endTime) {
        if (type.equals(DurationType.LAST_DAYS.name())) {
            return DateUtils.addDays(new Date(), -value);
        } else if (type.equals(DurationType.AHEAD_LENGTH.name())) {
            return null;
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!CollectionUtils.isEmpty(filterList)) {
            filterMap = filterList.stream().collect(Collectors.toMap(e -> e.streamKey(), e -> e));
        }
    }
}
