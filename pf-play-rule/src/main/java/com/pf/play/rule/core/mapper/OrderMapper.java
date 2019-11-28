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
     * @Description: 获取用户的待支付订单数据
     * @param model
     * @return List
     * @author yoko
     * @date 2019/11/28 11:21
     */
    public List<OrderModel> getUnpaidOrderList(OrderModel model);
}
