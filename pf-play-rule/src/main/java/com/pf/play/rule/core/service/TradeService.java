package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.consumer.ConsumerModel;
import com.pf.play.rule.core.model.trade.TradeModel;

/**
 * @Description 交易的Service层
 * @Author yoko
 * @Date 2019/11/26 22:42
 * @Version 1.0
 */
public interface TradeService<T> extends BaseService<T> {

    /**
     * @Description: 生成订单交易流水
     * @param tradeModel - 订单流水数据
     * @param sellConsumerModel - 卖家要冻结的资源
     * @return
     * @author yoko
     * @date 2019/12/2 11:57
    */
    public void createOrderTrade(TradeModel tradeModel, ConsumerModel sellConsumerModel) throws Exception;

    /**
     * @Description: 计算当前买量、今日成交量
     * <p>
     *     当前买量的查询条件：orderStatus=1；
     *     今日成交量的查询条件：orderStatus=3 and curday = 今天
     * </p>
     * @param model - orderStatus、curday
     * @param isCache - 是否通过缓存查询：0需要通过缓存查询，1直接查询数据库
     * @return String
     * @author yoko
     * @date 2019/12/2 14:45
    */
    public String getOrderTradeNum(TradeModel model, int isCache) throws Exception;
}
