package com.pf.play.rule.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pf.play.rule.core.common.dao.BaseDao;
import com.pf.play.rule.core.common.exception.ServiceException;
import com.pf.play.rule.core.common.service.impl.BaseServiceImpl;
import com.pf.play.rule.core.common.utils.constant.CachedKeyUtils;
import com.pf.play.rule.core.common.utils.constant.PfCacheKey;
import com.pf.play.rule.core.common.utils.constant.PfErrorCode;
import com.pf.play.rule.core.common.utils.constant.ServerConstant;
import com.pf.play.rule.core.mapper.ConsumerFixedMapper;
import com.pf.play.rule.core.mapper.OrderMapper;
import com.pf.play.rule.core.mapper.TradeMapper;
import com.pf.play.rule.core.model.consumer.ConsumerModel;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.model.trade.TradeModel;
import com.pf.play.rule.core.service.TradeService;
import com.pf.play.rule.util.ComponentUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 交易的Service层的实现层
 * @Author yoko
 * @Date 2019/11/26 22:43
 * @Version 1.0
 */
@Service
public class TradeServiceImpl<T> extends BaseServiceImpl<T> implements TradeService<T> {
    /**
     * 5分钟.
     */
    public long FIVE_MIN = 300;

    public long TWO_HOUR = 2;

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private ConsumerFixedMapper consumerFixedMapper;

    @Autowired
    private OrderMapper orderMapper;

    public BaseDao<T> getDao() {
        return tradeMapper;
    }

    @Override
    public void createOrderTrade(TradeModel tradeModel, ConsumerModel sellConsumerModel, OrderModel orderModel) throws Exception {
        //锁住这个订单号
        String lockKey = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_ORDER, tradeModel.getOrderNo());
        // redis锁定
        boolean flagLock = ComponentUtil.redisIdService.lock(lockKey);
        //锁住这个卖家的用户ID
        String lockKey_consumer = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_CONSUMER, sellConsumerModel.getMemberId());
        if (flagLock){

            // redis锁定
            boolean flagLock_consumer = ComponentUtil.redisIdService.lock(lockKey_consumer);
            if (flagLock_consumer){
                // 执行
                handleTrade(tradeModel, sellConsumerModel, orderModel);
            }else{
                throw new ServiceException(PfErrorCode.ENUM_ERROR.T00016.geteCode(), PfErrorCode.ENUM_ERROR.T00016.geteDesc());
            }
        }else {
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00015.geteCode(), PfErrorCode.ENUM_ERROR.T00015.geteDesc());
        }
        // 解锁
        ComponentUtil.redisIdService.delLock(lockKey);
        ComponentUtil.redisIdService.delLock(lockKey_consumer);
    }

    @Override
    public String getOrderTradeNum(TradeModel model, int isCache) throws Exception {
        String str;
        if (isCache == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            String strKeyCache = CachedKeyUtils.getPfCacheKey(PfCacheKey.ORDER_TRADE_NUM, model.getOrderStatus());
            String strCache = (String) ComponentUtil.redisService.get(strKeyCache);
            if (!StringUtils.isBlank(strCache)) {
                // 从缓存里面获取数据
                str = strCache;
            } else {
                //查询数据库
                str = tradeMapper.getOrderTradeNum(model);
                if (!StringUtils.isBlank(str)) {
                    // 把数据存入缓存
                    ComponentUtil.redisService.set(strKeyCache, str, FIVE_MIN);
                }
            }
        }else {
            // 直接查数据库
            // 查询数据库
            str = tradeMapper.getOrderTradeNum(model);
        }
        return str;
    }

    @Override
    public boolean updateTradeStatus(TradeModel model) throws Exception {
        int num = tradeMapper.updateTradeStatus(model);
        if (num == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00022.geteCode(), PfErrorCode.ENUM_ERROR.T00022.geteDesc());
        }
        return true;
    }

    @Override
    public void updateOrderAndOrderTradeStatus(TradeModel tradeModel, OrderModel orderModel) throws Exception {
        handleConfirmPay(tradeModel, orderModel);
    }

    @Override
    public void tradeFinish(TradeModel tradeModel, OrderModel orderModel, ConsumerModel sellConsumer, ConsumerModel buyConsumer) throws Exception {
        //锁住这个订单号
        String lockKey = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_ORDER, tradeModel.getOrderNo());
        boolean flagLock = ComponentUtil.redisIdService.lock(lockKey);
        //锁住这个卖家的用户ID
        String lockKey_sellConsumer = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_CONSUMER, sellConsumer.getMemberId());
        boolean flagLock_sellConsumer = ComponentUtil.redisIdService.lock(lockKey_sellConsumer);
        //锁住这个买家的用户ID
        String lockKey_buyConsumer = CachedKeyUtils.getPfCacheKey(PfCacheKey.LOCK_CONSUMER, buyConsumer.getMemberId());
        boolean flagLock_buyConsumer = ComponentUtil.redisIdService.lock(lockKey_buyConsumer);
        if (flagLock && flagLock_sellConsumer && flagLock_buyConsumer){
            // 正式执行
            handletradeFinish(tradeModel, orderModel, sellConsumer, buyConsumer);
        }else {
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00028.geteCode(), PfErrorCode.ENUM_ERROR.T00028.geteDesc());
        }
        ComponentUtil.redisIdService.delLock(lockKey);
        ComponentUtil.redisIdService.delLock(lockKey_sellConsumer);
        ComponentUtil.redisIdService.delLock(lockKey_buyConsumer);
    }

    /**
     * @Description: 执行订单交易
     * @param tradeModel - 订单交易流水信息
     * @param sellConsumerModel - 卖家信息
     * @return
     * @author yoko
     * @date 2019/12/2 13:54
    */
    @Transactional
    public void handleTrade(TradeModel tradeModel, ConsumerModel sellConsumerModel, OrderModel orderModel){
        tradeMapper.add(tradeModel);
        consumerFixedMapper.updateConsumerMasonry(sellConsumerModel);
        orderMapper.updateOrderStatus(orderModel);
    }

    /**
     * @Description: 买家确认支付
     * @param tradeModel - 订单交易流水信息
     * @param orderModel - 订单流水
     * @return
     * @author yoko
     * @date 2019/12/2 13:54
     */
    @Transactional
    public void handleConfirmPay(TradeModel tradeModel, OrderModel orderModel) throws Exception{
        int num_trade = tradeMapper.updateTradeStatus(tradeModel);
        if (num_trade == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00022.geteCode(), PfErrorCode.ENUM_ERROR.T00022.geteDesc());
        }
        int num_order = orderMapper.updateOrderStatus(orderModel);
        if (num_order == ServerConstant.PUBLIC_CONSTANT.SIZE_VALUE_ZERO){
            throw new ServiceException(PfErrorCode.ENUM_ERROR.T00026.geteCode(), PfErrorCode.ENUM_ERROR.T00026.geteDesc());
        }
    }

    /**
     * @Description: 事物处理：完成最后的交易
     * @param tradeModel - 订单交易流水的状态修改
     * @param orderModel - 订单流水的状态修改
     * @param sellConsumer - 卖家冻结的钻石修改
     * @param buyConsumer - 买家添加买入的钻石
     * @return
     * @author yoko
     * @date 2019/12/3 21:32
    */
    @Transactional
    public void handletradeFinish(TradeModel tradeModel, OrderModel orderModel, ConsumerModel sellConsumer, ConsumerModel buyConsumer){
        tradeMapper.updateTradeStatus(tradeModel);
        orderMapper.updateOrderStatus(orderModel);
        consumerFixedMapper.updateConsumerSubtractMasonry(sellConsumer);
        consumerFixedMapper.updateConsumerAddMasonry(buyConsumer);
    }
}
