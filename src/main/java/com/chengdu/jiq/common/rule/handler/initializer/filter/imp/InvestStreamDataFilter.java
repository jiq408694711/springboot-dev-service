package com.chengdu.jiq.common.rule.handler.initializer.filter.imp;

/**
 * Created by jiyiqin on 2018/5/20.
 */

import com.chengdu.jiq.common.rule.handler.initializer.filter.AbstractStreamFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiyiqin on 2018/5/20.
 */
@Component
public class InvestStreamDataFilter extends AbstractStreamFilter {
    @Override
    public String streamKey() {
        return "STREAM_INVEST";
    }

    @Override
    public List<Map<String, Object>> filter() {
        Map<String, Object> invest1 = new HashMap<>();
        invest1.put("userId", 33012);
        invest1.put("investAmount", 100000);

        Map<String, Object> invest2 = new HashMap<>();
        invest2.put("userId", 33012);
        invest2.put("investAmount", 10000);

        Map<String, Object> invest3 = new HashMap<>();
        invest3.put("userId", 33012);
        invest3.put("investAmount", 400090);

        Map<String, Object> invest4 = new HashMap<>();
        invest4.put("userId", 33012);
        invest4.put("investAmount", 500);

        Map<String, Object> invest5 = new HashMap<>();
        invest5.put("userId", 33012);
        invest5.put("investAmount", 4000);
        return Arrays.asList(invest1, invest2, invest3, invest4, invest5);
    }
}