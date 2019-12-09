package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.consumer.ConsumerModel;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.model.task.base.StatusModel;
import com.pf.play.rule.core.model.violate.OrderViolateModel;

/**
 * @Description 订单交易流水的Task的Service层
 * @Author yoko
 * @Date 2019/12/6 21:39
 * @Version 1.0
 */
public interface TaskOrderTradeService<T> extends BaseService<T> {

    /**
     * @Description: 买家超时执行的方法
     * <p>买家超时：A.纪录买家超时违约。 B.修改订单超时状态。 C.卖家钻石解冻 D.订单交易流水的runStatus状态更新成完成</p>
     * @param orderViolateModel - 违约数据
     * @param orderModel - 订单信息
     * @param consumerModel - 用户资源信息
     * @param statusModel - 订单交易流水的状态更新
     * @return void
     * @author yoko
     * @date 2019/12/9 20:05
    */
    public void taskActoinByBuy(OrderViolateModel orderViolateModel, OrderModel orderModel, ConsumerModel consumerModel, StatusModel statusModel) throws Exception;
}
