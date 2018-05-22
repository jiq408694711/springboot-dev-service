package com.chengdu.jiq.common.rule.model.condition;

import com.chengdu.jiq.common.rule.model.DrCondition;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jiyiqin on 2018/5/22.
 */
public class BaseCondition extends DrCondition {
    private List<MetaCondition> metaConditions;

//    public static BaseCondition newBaseCondition(List<MetaCondition> metaConditions) {
//        BaseCondition drCondition = new BaseCondition();
//        drCondition.setMetaConditions(metaConditions);
//        return drCondition;
//    }

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
