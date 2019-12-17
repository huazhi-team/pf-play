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
     * @Description: 获取用户的待支付、待确认付款订单数据-详情
     * @param model
     * @return OrderModel
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public OrderModel getUnpaidOrder(OrderModel model) throws Exception;

    /**
     * @Description: 获取用户的待支付、待收款订单数据-列表
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


    /**
     * @Description: 获取用户的已完成的订单数据-详情
     * <p>
     *     注意:客户端会传orderNo、orderType这两个参数；
     *     因为涉及到求购以及卖出的两种类型订单，所以调用的SQL语句使用了2个方法
     * </p>
     * @param model
     * @return List
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public OrderModel getFinishOrder(OrderModel model) throws Exception;


    /**
     * @Description: 获取用户的已完成的订单数据-列表
     * @param model
     * @return List
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public List<OrderModel> getFinishOrderList(OrderModel model) throws Exception;

    /**
     * @Description: 更新订单流水的申诉状态
     * @param model
     * @return int
     * @author yoko
     * @date 2019/12/6 17:10
     */
    public int updateOrderAppealStatus(OrderModel model);

    /**
     * @Description: 更新订单超时的状态
     * @param model - 订单信息
     * @return int
     * @author yoko
     * @date 2019/12/10 10:05
    */
    public int updateOrderOverTime(OrderModel model);


    /**
     * @Description: 获取已超时订单数据-详情
     * <p>
     *     注意:客户端会传orderNo、orderType这两个参数；
     *     因为涉及到求购以及卖出的两种类型订单，所以调用的SQL语句使用了2个方法
     * </p>
     * @param model
     * @return List
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public OrderModel getOverTimeOrder(OrderModel model) throws Exception;



    /**
     * @Description: 获取已超时订单数据-列表
     * <p>
     *     超时订单分两部分订单：求购订单（买家确认支付超时）、卖出订单（卖家确认收款超时）
     * </p>
     * @param model
     * @return List
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public List<OrderModel> getOverTimeOrderList(OrderModel model) throws Exception;


}
