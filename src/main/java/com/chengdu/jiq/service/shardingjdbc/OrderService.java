package com.chengdu.jiq.service.shardingjdbc;

import com.chengdu.jiq.mapper.sharding.OrderMapper;
import com.chengdu.jiq.model.bo.OrderModel;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class OrderService {

    @Inject
    private OrderMapper orderMapper;

    public int insert(OrderModel model) {
        return orderMapper.insert(model);
    }

    public int batchInsert(List<OrderModel> models) {
        return 0;
    }
}
