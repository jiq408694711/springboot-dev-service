package com.chengdu.jiq.service.rules.initializer.stream;

/**
 * Created by jiyiqin on 2018/5/20.
 */

import com.chengdu.jiq.common.engine.initializer.stream.AbstractStreamFilter;
import com.chengdu.jiq.service.rules.model.InvestMessage;
import com.chengdu.jiq.service.rules.repository.UserInvestMessageRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jiyiqin on 2018/5/20.
 */
@Component
public class InvestStreamDataFilter extends AbstractStreamFilter {
    @Override
    public String streamKey() {
        return "STREAM_INVEST";
    }

    @Autowired
    private UserInvestMessageRepository uimRepository;

    @Override
    public List<Map<String, Object>> filter(Map<String, Object> data, Date beginDate, Date endDate, Integer limit) {
        if (!data.containsKey("aId") || data.get("aId") == null) {
            return null;
        }
        //search invest records from dataBase
        List<InvestMessage> list = uimRepository.select(data.get("aId").toString(), beginDate, endDate, limit);
        return list.stream().map(m -> {
            Map<String, Object> map = new HashMap<>();
            map.put("investAmount", m.getInvestAmount());
            map.put("investTime", DateUtils.addDays(new Date(), -11));
            map.put("registerTime", DateUtils.addDays(new Date(), -20));
            return map;
        }).collect(Collectors.toList());
    }
}