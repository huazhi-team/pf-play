package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.consumer.ConsumerModel;
import com.pf.play.rule.core.model.order.OrderModel;
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
    public void createOrderTrade(TradeModel tradeModel, ConsumerModel sellConsumerModel, OrderModel orderModel) throws Exception;

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

    /**
     * @Description: 修改订单状态：交易状态：1超时，2正常进行中，3问题申诉，4确认已付款（买家等待），5确认已收款（卖家确认）
     * <p>
     *     买家确认付款时：会有图片值这个字段
     * </p>
     * @param tradeModel - 订单交易流水
     * @return boolean
     * @author yoko
     * @date 2019/12/3 11:31
     */
    public boolean updateTradeStatus(TradeModel tradeModel) throws Exception;

    /**
     * @Description: 修改订单流水的交易状态；修改订单交易流水的订单状态：交易状态：1超时，2正常进行中，3问题申诉，4确认已付款（买家等待），5确认已收款（卖家确认）
     * <p>
     *     买家确认付款时：会有图片值这个字段
     * </p>
     * @param tradeModel - 订单交易流水
     * @param orderModel - 订单流水
     * @return void
     * @author yoko
     * @date 2019/12/3 11:31
     */
    public void updateOrderAndOrderTradeStatus(TradeModel tradeModel, OrderModel orderModel) throws Exception;

    /**
     * @Description: 交易完成的方法
     * <p>
     *     A.修改订单流水里面的状态：
     *      1.订单交易状态：修改成完成
     *      2.订单状态：修改成完成交易
     *    B.修改订单交易流水里面的状态：
     *      1.交易状态：修改成确认已收款（卖家确认）
     *    C.卖家冻结的钻石扣除
     *    D.买家添加购买的钻石
     * </p>
     * @param tradeModel - 订单交易流水的状态修改
     * @param orderModel - 订单流水的状态修改
     * @param sellConsumer - 卖家冻结的钻石修改
     * @param buyConsumer - 买家添加买入的钻石
     * @return void
     * @author yoko
     * @date 2019/12/3 21:20
    */
    public void tradeFinish(TradeModel tradeModel, OrderModel orderModel, ConsumerModel sellConsumer, ConsumerModel buyConsumer) throws Exception;

    /**
     * @Description: 更新订单交易流水的申诉状态
     * @param model
     * @return int
     * @author yoko
     * @date 2019/12/6 17:10
    */
    public int updateTradeAppealStatus(TradeModel model);
}
