package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.PfCacheKey;
import com.pf.play.rule.core.common.utils.constant.PfErrorCode;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.mapper.OrderMapper;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.service.OrderService;
import com.pf.play.rule.util.ComponentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Description 订单流水的Service层的实现层
 * @Author yoko
 * @Date 2019/11/23 17:19
 * @Version 1.0
 */
@Service
public class OrderServiceImpl<T> extends BaseServiceImpl<T> implements OrderService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private OrderMapper orderMapper;

    public BaseDao<T> getDao() {
        return orderMapper;
    }

    @Override
    public int cancelOrder(OrderModel model) throws Exception {
        int num;
        String lockKey = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_ORDER, model.getOrderNo());
        // redis锁定
        boolean flagLock = ComponentUtil.redisIdService.lock(lockKey);
        if (flagLock){
            num = orderMapper.cancelOrder(model);
        }else {
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00010.geteCode(), PfErrorCode.ENUM_ERROR.D00010.geteDesc());
        }
        // 解锁
        ComponentUtil.redisIdService.delLock(lockKey);
        return num;
    }

    @Override
    public OrderModel getUnpaidOrder(OrderModel model) throws Exception {
        return orderMapper.getUnpaidOrder(model);
    }

    @Override
    public List<OrderModel> getUnpaidOrderList(OrderModel model) throws Exception {
        Integer rowCount = orderMapper.countUnpaidOrder(model);
        model.setRowCount(rowCount);
        List<OrderModel> list = orderMapper.getUnpaidOrderList(model);
        return list;
    }

    @Override
    public boolean updateOrderStatus(OrderModel model) throws Exception {
        int num = orderMapper.updateOrderStatus(model);
        if (num == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.D00012.geteCode(), PfErrorCode.ENUM_ERROR.D00012.geteDesc());
        }
        return true;
    }

    @Override
    public OrderModel getFinishOrder(OrderModel model) throws Exception {
        // 订单类型：1求购订单，2卖出订单
        if (model.getOrderType() == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE){
            // 1求购订单
            return orderMapper.getFinishOrderByBuy(model);
        }else if (model.getOrderType() == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO){
            // 2卖出订单
            return orderMapper.getFinishOrderBySell(model);
        }else {
            return null;
        }
    }


    @Override
    public List<OrderModel> getFinishOrderList(OrderModel model) throws Exception {
        Integer rowCount = orderMapper.countFinishOrder(model);
        model.setRowCount(rowCount);
        List<OrderModel> list = orderMapper.getFinishOrderList(model);
        return list;
    }

    @Override
    public int updateOrderAppealStatus(OrderModel model) {
        return orderMapper.updateOrderAppealStatus(model);
    }

    @Override
    public int updateOrderOverTime(OrderModel model) {
        return orderMapper.updateOrderOverTime(model);
    }

    @Override
    public OrderModel getOverTimeOrder(OrderModel model) throws Exception {
        // 订单类型：1求购订单，2卖出订单
        if (model.getOrderType() == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ONE){
            // 1求购订单
            return orderMapper.getOverTimeOrderByBuy(model);
        }else if (model.getOrderType() == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_TWO){
            // 2卖出订单
            return orderMapper.getOverTimeOrderBySell(model);
        }else {
            return null;
        }
    }

    @Override
    public List<OrderModel> getOverTimeOrderList(OrderModel model) throws Exception {
        Integer rowCount = orderMapper.countOverTimeOrder(model);
        model.setRowCount(rowCount);
        List<OrderModel> list = orderMapper.getOverTimeOrderList(model);
        return list;
    }
}
