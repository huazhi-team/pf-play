package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.order.OrderModel;


/**
 * @Description 订单流水的Service层
 * @Author yoko
 * @Date 2019/11/23 17:17
 * @Version 1.0
 */
public interface OrderService<T> extends BaseService<T> {

    /**
     * @Description: 取消订单
     * @param model - 订单信息
     * @return int
     * @author yoko
     * @date 2019/11/26 21:49
    */
    public int cancelOrder(OrderModel model) throws Exception;
}
