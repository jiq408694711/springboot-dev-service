package com.chengdu.jiq.common.engine.condition.enums;

/**
 * Created by jiyiqin on 2018/5/16.
 */
public enum CompareMethod {
    GRATER,
    GRATER_AND_QUEAL,
    LESS,
    LESS_AND_EQUAL,
    EQUAL,
    MATCH,  //(Buffalo)?\\S*Mozzarella
    NOT_MATCH,
    CONTAINS,
    NOT_CONTAINS,
    MEMBERS_OF,
    NOT_MEMBERS_OF,
    IN,
    NOT_IN,
    STR_STARTS_WITH,
    STR_END_WITH,
    STR_LENGTH,
    BETWEEN,
    ;
}
