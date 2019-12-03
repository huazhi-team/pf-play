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
import com.pf.play.rule.core.mapper.TradeMapper;
import com.pf.play.rule.core.model.consumer.ConsumerModel;
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

    public BaseDao<T> getDao() {
        return tradeMapper;
    }

    @Override
    public void createOrderTrade(TradeModel tradeModel, ConsumerModel sellConsumerModel) throws Exception {
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
                handleTrade(tradeModel, sellConsumerModel);
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
//                int num = tradeMapper.getOrderTradeNum(model);
//                str = String.valueOf(num);
                str = tradeMapper.getOrderTradeNum(model);
                if (!StringUtils.isBlank(str)) {
                    // 把数据存入缓存
                    ComponentUtil.redisService.set(strKeyCache, str, FIVE_MIN);
                }
            }
        }else {
            // 直接查数据库
            // 查询数据库
//            int num = tradeMapper.getOrderTradeNum(model);
            str = tradeMapper.getOrderTradeNum(model);
//            str = String.valueOf(num);
        }
        return str;
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
    public void handleTrade(TradeModel tradeModel, ConsumerModel sellConsumerModel){
        tradeMapper.add(tradeModel);
        consumerFixedMapper.updateConsumerMasonry(sellConsumerModel);
    }
}
