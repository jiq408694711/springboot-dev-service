package com.chengdu.jiq.service.rules;

import java.util.List;

/**
 * Created by jiyiqin on 2018/3/31.
 */
public class DroolsUtils {

    public static void initContext(List<String> keys) {
        System.out.println("当前规则需要初始化的条件包括：" + keys);
    }
}
