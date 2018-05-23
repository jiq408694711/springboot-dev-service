package com.chengdu.jiq.common.rule.model.enums;

/**
 * Created by jiyiqin on 2018/5/17.
 */
public enum DurationType {
    NATURE_DAY,
    NATURE_WEEK,
    NATURE_MONTH,
    LAST_DAYS,
    LAST_HOURS,
    LAST_MINUTES,
    LAST_SECONDS,
    DURATION_TIME,  //DATE

    AHEAD_LENGTH,   //LIMIT
    LAST_LENGTH,

    NATURE_DAY_AHEAD_LENGTH,
    NATURE_WEEK_AHEAD_LENGTH,
    NATURE_MONTH_AHEAD_LENGTH,
    DURATION_TIME_AHEAD_LENGTH,    //DATE+LENGTH
}
