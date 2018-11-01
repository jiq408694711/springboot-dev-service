package com.chengdu.jiq.mapper.sharding;

import com.chengdu.jiq.model.bo.OrderModel;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created by jiyiqin on 2017/11/6.
 */
@Mapper
public interface OrderMapper {

    int insert(OrderModel model);

    List<OrderModel> getAll();
}
