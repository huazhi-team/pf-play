package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.mapper.ConsumerFixedMapper;
import com.pf.play.rule.core.mapper.OrderMapper;
import com.pf.play.rule.core.mapper.OrderViolateMapper;
import com.pf.play.rule.core.mapper.TaskOrderTradeMapper;
import com.pf.play.rule.core.model.consumer.ConsumerModel;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.model.task.base.StatusModel;
import com.pf.play.rule.core.model.violate.OrderViolateModel;
import com.pf.play.rule.core.service.TaskOrderTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 订单交易流水的Task的Service层的实现层
 * @Author yoko
 * @Date 2019/12/6 21:41
 * @Version 1.0
 */
@Service
public class TaskOrderTradeServiceImpl<T> extends BaseServiceImpl<T> implements TaskOrderTradeService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private TaskOrderTradeMapper taskOrderTradeMapper;

    @Autowired
    private OrderViolateMapper orderViolateMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ConsumerFixedMapper consumerFixedMapper;


    public BaseDao<T> getDao() {
        return taskOrderTradeMapper;
    }

    @Override
    public void taskActoinByBuy(OrderViolateModel orderViolateModel, OrderModel orderModel, ConsumerModel consumerModel, StatusModel statusModel) throws Exception {
        handleTaskActoinByBuy(orderViolateModel, orderModel, consumerModel, statusModel);
    }

    /**
     * @Description: 买家超时执行的数据
     * @param orderViolateModel - 违约数据
     * @param orderModel - 订单信息
     * @param consumerModel - 用户要加减钻石的信息
     * @param statusModel - 运行数据更新状态
     * @return void
     * @author yoko
     * @date 2019/12/10 10:45
     */
    @Transactional
    public void handleTaskActoinByBuy(OrderViolateModel orderViolateModel, OrderModel orderModel, ConsumerModel consumerModel, StatusModel statusModel){
        orderViolateMapper.add(orderViolateModel);
        orderMapper.updateOrderOverTime(orderModel);
        consumerFixedMapper.updateConsumerMasonryByThaw(consumerModel);
        taskOrderTradeMapper.update(statusModel);
    }
}
