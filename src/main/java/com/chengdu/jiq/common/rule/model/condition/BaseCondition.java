package com.chengdu.jiq.common.rule.model.condition;

import com.chengdu.jiq.common.rule.model.DrCondition;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jiyiqin on 2018/5/22.
 * 基础条件的数据来源于当前传入数据、或外部获取数据
 */
public class BaseCondition extends DrCondition {
    private List<MetaCondition> metaConditions; //或关系的多个原子条件

    public static BaseCondition newBaseCondition(MetaCondition... metaConditions) {
        BaseCondition drCondition = new BaseCondition();
        drCondition.setMetaConditions(Arrays.asList(metaConditions));
        return drCondition;
    }

    public List<MetaCondition> getMetaConditions() {
        return metaConditions;
    }

    public void setMetaConditions(List<MetaCondition> metaConditions) {
        this.metaConditions = metaConditions;
    }
}
