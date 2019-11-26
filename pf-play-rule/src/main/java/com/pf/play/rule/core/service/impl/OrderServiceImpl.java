package com.pf.play.rule.core.service.impl;

import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.PfCacheKey;
import com.pf.play.rule.core.common.utils.constant.PfErrorCode;
import com.pf.play.rule.core.mapper.OrderMapper;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.service.OrderService;
import com.pf.play.rule.util.ComponentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}
