package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.order.OrderModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description 订单流水的Dao层
 * @Author yoko
 * @Date 2019/11/23 17:19
 * @Version 1.0
 */
@Mapper
public interface OrderMapper<T> extends BaseDao<T> {

    /**
     * @Description: 取消订单
     * @param model - 订单信息
     * @return int
     * @author yoko
     * @date 2019/11/26 21:49
     */
    public int cancelOrder(OrderModel model);

    /**
     * @Description: 获取用户的待支付订单数据的总行数
     * @param model
     * @return List
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public int countUnpaidOrder(OrderModel model);


    /**
     * @Description: 获取用户的待支付、待确认付款订单数据-详情
     * @param model
     * @return OrderModel
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public OrderModel getUnpaidOrder(OrderModel model);

    /**
     * @Description: 获取用户的待支付订单数据
     * @param model
     * @return List
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public List<OrderModel> getUnpaidOrderList(OrderModel model);

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
    public int updateOrderStatus(OrderModel model);


    /**
     * @Description: 获取用户的已完成的订单数据的总行数
     * @param model
     * @return List
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public int countFinishOrder(OrderModel model);

    /**
     * @Description: 获取用户的已完成的订单数据-买入订单-详情
     * @param model
     * @return List
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public OrderModel getFinishOrderByBuy(OrderModel model);

    /**
     * @Description: 获取用户的已完成的订单数据-卖出订单-详情
     * @param model
     * @return List
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public OrderModel getFinishOrderBySell(OrderModel model);

    /**
     * @Description: 获取用户的已完成的订单数据-列表
     * @param model
     * @return List
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public List<OrderModel> getFinishOrderList(OrderModel model);

    /**
     * @Description: 更新订单流水的申诉状态
     * @param model
     * @return int
     * @author yoko
     * @date 2019/12/6 17:10
     */
    public int updateOrderAppealStatus(OrderModel model);

    /**
     * @Description: 更新订单是否超时
     * @param model - 订单信息
     * @return int
     * @author yoko
     * @date 2019/12/10 10:05
     */
    public int updateOrderOverTime(OrderModel model);
}
