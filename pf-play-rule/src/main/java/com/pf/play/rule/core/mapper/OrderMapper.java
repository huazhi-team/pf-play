package com.pf.play.rule.core.mapper;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.model.order.OrderModel;
import org.apache.ibatis.annotations.Mapper;

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
}
