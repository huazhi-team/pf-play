package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.order.OrderModel;

import java.util.List;


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

    /**
     * @Description: 获取用户的待支付订单数据
     * @param model
     * @return List
     * @author yoko
     * @date 2019/11/28 11:21
    */
    public List<OrderModel> getUnpaidOrderList(OrderModel model) throws Exception;

    /**
     * @Description: 修改订单状态：订单交易状态、订单状态
     * <p>
     *     订单交易状态：0初始化，1锁定，2确认付款，3完成
     *     订单状态：1正常，2取消，3完成交易
     * </p>
     * @param model
     * @return boolean
     * @author yoko
     * @date 2019/12/3 11:31
    */
    public boolean updateOrderStatus(OrderModel model) throws Exception;


}
