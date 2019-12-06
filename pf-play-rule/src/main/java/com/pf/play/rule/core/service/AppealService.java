package com.pf.play.rule.core.service;

import com.pf.play.rule.core.common.service.BaseService;
import com.pf.play.rule.core.model.appeal.AppealModel;
import com.pf.play.rule.core.model.order.OrderModel;
import com.pf.play.rule.core.model.trade.TradeModel;

/**
 * @Description 申诉订单的Service层
 * @Author yoko
 * @Date 2019/12/5 10:25
 * @Version 1.0
 */
public interface AppealService<T> extends BaseService<T> {

    /**
     * @Description: 申诉人更新资料原因-积极的
     * @param model
     * @return void
     * @author yoko
     * @date 2019/12/5 19:45
    */
    public void updateActive(AppealModel model);

    /**
     * @Description: 被申诉人更新资料原因-被动的反驳方
     * @param model
     * @return void
     * @author yoko
     * @date 2019/12/5 19:45
     */
    public void updatePassive(AppealModel model);


    /**
     * @Description: 新增申诉数据
     * <p>
     *     此方法内涵事物；
     *     A.添加申诉数据
     *     B.更新订单流水的申诉状态：更新成申诉状态
     *     C.更新订单交易流水的申诉状态：更新成申诉状态
     * </p>
     * @param appealModel - 申诉的新增数据
     * @param orderModel - 订单流水要更新成申诉的状态数据
     * @param tradeModel - 订单交易流水要更新成申诉的状态数据
     * @return
     * @author yoko
     * @date 2019/12/6 17:38
    */
    public void addAppeal(AppealModel appealModel, OrderModel orderModel, TradeModel tradeModel) throws Exception;
}
